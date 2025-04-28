#define _XOPEN_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <locale.h>

#define MAX_LINE 1024
#define MAX_FIELDS 12
#define MAX_STR 256
#define MAX_ARRAY 20

typedef struct {
    char show_id[MAX_STR];
    char type[MAX_STR];
    char title[MAX_STR];
    char director[MAX_STR];
    char cast[MAX_ARRAY][MAX_STR];
    int cast_size;
    char country[MAX_STR];
    char date_added[MAX_STR];
    int release_year;
    char rating[MAX_STR];
    char duration[MAX_STR];
    char listed_in[MAX_ARRAY][MAX_STR];
    int listed_in_size;
} Show;

void inicializarShow(Show *s) {
    strcpy(s->show_id, "NaN");
    strcpy(s->type, "NaN");
    strcpy(s->title, "NaN");
    strcpy(s->director, "NaN");
    strcpy(s->cast[0], "NaN");
    s->cast_size = 1;
    strcpy(s->country, "NaN");
    strcpy(s->date_added, "NaN");
    s->release_year = -1;
    strcpy(s->rating, "NaN");
    strcpy(s->duration, "NaN");
    strcpy(s->listed_in[0], "NaN");
    s->listed_in_size = 1;
}

void removerAspas(char *str) {
    char temp[MAX_STR];
    int j = 0;
    for (int i = 0; str[i]; i++) {
        if (str[i] != '"') temp[j++] = str[i];
    }
    temp[j] = '\0';
    strcpy(str, temp);
}

int dividirCampo(char *campo, char array[MAX_ARRAY][MAX_STR]) {
    int count = 0;
    char *token = strtok(campo, ",");
    while (token && count < MAX_ARRAY) {
        while (*token == ' ') token++;
        removerAspas(token);
        strncpy(array[count++], token, MAX_STR);
        token = strtok(NULL, ",");
    }
    return count;
}

void ordenarArray(char array[MAX_ARRAY][MAX_STR], int size) {
    char temp[MAX_STR];
    for (int i = 0; i < size - 1; i++) {
        for (int j = i + 1; j < size; j++) {
            if (strcasecmp(array[i], array[j]) > 0) {
                strcpy(temp, array[i]);
                strcpy(array[i], array[j]);
                strcpy(array[j], temp);
            }
        }
    }
}

void imprimirShow(Show *s) {
    char dataFormatada[50] = "NaN";
    if (strcmp(s->date_added, "NaN") != 0) {
        struct tm tm = {0};
        strptime(s->date_added, "%b %d, %Y", &tm);
        setlocale(LC_TIME, "en_US.UTF-8");
        strftime(dataFormatada, sizeof(dataFormatada), "%B %e, %Y", &tm);
        for (int i = 0; dataFormatada[i]; i++) {
            if (dataFormatada[i] == ' ' && dataFormatada[i + 1] == ' ') {
                memmove(&dataFormatada[i], &dataFormatada[i + 1], strlen(&dataFormatada[i + 1]) + 1);
            }
        }
    }

    ordenarArray(s->cast, s->cast_size);
    printf("=> %s ## %s ## %s ## %s ## [", s->show_id, s->title, s->type, s->director);
    for (int i = 0; i < s->cast_size; i++) {
        printf("%s", s->cast[i]);
        if (i < s->cast_size - 1) printf(", ");
    }
    printf("] ## %s ## %s ## %d ## %s ## %s ## [", s->country, dataFormatada, s->release_year, s->rating, s->duration);
    for (int i = 0; i < s->listed_in_size; i++) {
        printf("%s", s->listed_in[i]);
        if (i < s->listed_in_size - 1) printf(", ");
    }
    printf("] ##\n");
}

int parseCSVLine(const char *linha, char campos[MAX_FIELDS][MAX_STR]) {
    int i = 0, inQuotes = 0, col = 0, ci = 0;
    char ch;
    while ((ch = linha[i++]) != '\0') {
        if (ch == '"') {
            inQuotes = !inQuotes;
        } else if (ch == ',' && !inQuotes) {
            campos[col][ci] = '\0';
            col++;
            ci = 0;
        } else {
            campos[col][ci++] = ch;
        }
    }
    campos[col][ci] = '\0';
    return col + 1;
}

int buscarPorID(const char *idBuscado, const char *arquivoCSV, Show *s) {
    FILE *file = fopen(arquivoCSV, "r");
    if (!file) {
        perror("Erro ao abrir o arquivo");
        return 0;
    }

    char linha[MAX_LINE];
    fgets(linha, sizeof(linha), file); // cabeçalho

    while (fgets(linha, sizeof(linha), file)) {
        linha[strcspn(linha, "\n")] = '\0';
        char campos[MAX_FIELDS][MAX_STR];
        int numCampos = parseCSVLine(linha, campos);
        if (strcmp(campos[0], idBuscado) != 0) continue;

        inicializarShow(s);
        strcpy(s->show_id, campos[0]);
        strcpy(s->type, campos[1]);
        strcpy(s->title, campos[2]);
        strcpy(s->director, strlen(campos[3]) ? campos[3] : "NaN");

        if (strlen(campos[4])) {
            s->cast_size = dividirCampo(campos[4], s->cast);
        }

        strcpy(s->country, strlen(campos[5]) ? campos[5] : "NaN");
        strcpy(s->date_added, strlen(campos[6]) ? campos[6] : "NaN");
        s->release_year = strlen(campos[7]) ? atoi(campos[7]) : -1;
        strcpy(s->rating, strlen(campos[8]) ? campos[8] : "NaN");
        strcpy(s->duration, strlen(campos[9]) ? campos[9] : "NaN");

        if (strlen(campos[10])) {
            s->listed_in_size = dividirCampo(campos[10], s->listed_in);
        }

        fclose(file);
        return 1;
    }

    fclose(file);
    return 0;
}

int main() {
    char entrada[MAX_STR];
    const char *arquivo = "/tmp/disneyplus.csv";
    //const char *arquivo = "./disneyplus.csv";

    while (fgets(entrada, sizeof(entrada), stdin)) {
        entrada[strcspn(entrada, "\n")] = '\0';
        if (strcmp(entrada, "FIM") == 0) break;

        Show s;
        if (buscarPorID(entrada, arquivo, &s)) {
            imprimirShow(&s);
        } else {
            printf("=> NaN ## NaN ## NaN ## NaN ## [NaN] ## NaN ## NaN ## -1 ## NaN ## NaN ## [NaN] ##\n");
        }
    }

    return 0;
}