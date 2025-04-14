#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MAX_CAST 10
#define MAX_LISTED 10
#define MAX_LINE 1024

typedef struct {
    char show_id[100];
    char type[100];
    char title[200];
    char director[100];
    char cast[MAX_CAST][100];
    int cast_count;
    char country[100];
    char date_added[100];
    int release_year;
    char rating[20];
    char duration[50];
    char listed_in[MAX_LISTED][100];
    int listed_count;
} Show;

// Função para remover aspas de uma string
void remover_aspas(char* str) {
    char temp[200];
    int j = 0;
    for (int i = 0; str[i]; i++) {
        if (str[i] != '"') {
            temp[j++] = str[i];
        }
    }
    temp[j] = '\0';
    strcpy(str, temp);
}

// Ordenar vetor de strings (cast)
void ordenar(char arr[][100], int n) {
    char temp[100];
    for (int i = 0; i < n-1; i++) {
        for (int j = i+1; j < n; j++) {
            if (strcasecmp(arr[i], arr[j]) > 0) {
                strcpy(temp, arr[i]);
                strcpy(arr[i], arr[j]);
                strcpy(arr[j], temp);
            }
        }
    }
}

// Mês por extenso
const char* nome_mes(int mes) {
    const char* meses[] = {
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    };
    return (mes >= 1 && mes <= 12) ? meses[mes - 1] : "Unknown";
}

// Imprimir show formatado
void imprimir(Show s) {
    remover_aspas(s.title);
    for (int i = 0; i < s.cast_count; i++) remover_aspas(s.cast[i]);
    ordenar(s.cast, s.cast_count);

    int dia, mes, ano;
    if (strcmp(s.date_added, "NaN") == 0 || sscanf(s.date_added, "%*s %d, %d", &dia, &ano) != 2) {
        printf("=> %s ## %s ## %s ## %s ## [", s.show_id, s.title, s.type, s.director);
    } else {
        char mes_str[10];
        sscanf(s.date_added, "%s", mes_str);
        char* meses[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        for (int i = 0; i < 12; i++) {
            if (strcasecmp(mes_str, meses[i]) == 0) {
                mes = i + 1;
                break;
            }
        }
        printf("=> %s ## %s ## %s ## %s ## [", s.show_id, s.title, s.type, s.director);
    }

    for (int i = 0; i < s.cast_count; i++) {
        printf("%s%s", s.cast[i], (i < s.cast_count - 1) ? ", " : "");
    }

    printf("] ## %s ## ", s.country);

    if (strcmp(s.date_added, "NaN") != 0 && mes >= 1 && mes <= 12) {
        printf("%s %02d, %d", nome_mes(mes), dia, ano);
    } else {
        printf("NaN");
    }

    printf(" ## %d ## %s ## %s ## [", s.release_year, s.rating, s.duration);

    for (int i = 0; i < s.listed_count; i++) {
        printf("%s%s", s.listed_in[i], (i < s.listed_count - 1) ? ", " : "");
    }

    printf("] ##\n");
}

// Processar linha CSV
Show ler(char* linha) {
    Show s;
    char* campos[12];
    int i = 0;

    for (char* tok = strtok(linha, ","); tok && i < 12; tok = strtok(NULL, ",")) {
        campos[i++] = tok;
    }

    strcpy(s.show_id, i > 0 ? campos[0] : "NaN");
    strcpy(s.type, i > 1 ? campos[1] : "NaN");
    strcpy(s.title, i > 2 ? campos[2] : "NaN");
    strcpy(s.director, i > 3 ? campos[3] : "NaN");

    s.cast_count = 0;
    if (i > 4 && strcmp(campos[4], "") != 0) {
        char* tok = strtok(campos[4], ";");
        while (tok && s.cast_count < MAX_CAST) {
            strcpy(s.cast[s.cast_count++], tok);
            tok = strtok(NULL, ";");
        }
    } else {
        strcpy(s.cast[0], "NaN");
        s.cast_count = 1;
    }

    strcpy(s.country, i > 5 ? campos[5] : "NaN");
    strcpy(s.date_added, i > 6 ? campos[6] : "NaN");
    s.release_year = (i > 7 && strcmp(campos[7], "") != 0) ? atoi(campos[7]) : -1;
    strcpy(s.rating, i > 8 ? campos[8] : "NaN");
    strcpy(s.duration, i > 9 ? campos[9] : "NaN");

    s.listed_count = 0;
    if (i > 10 && strcmp(campos[10], "") != 0) {
        char* tok = strtok(campos[10], ";");
        while (tok && s.listed_count < MAX_LISTED) {
            strcpy(s.listed_in[s.listed_count++], tok);
            tok = strtok(NULL, ";");
        }
    } else {
        strcpy(s.listed_in[0], "NaN");
        s.listed_count = 1;
    }

    return s;
}

int main() {
    //FILE* f = fopen("/tmp/disneyplus.csv", "r");//verde
    FILE* f = fopen("disneyplus.csv", "r");//maquina
    if (!f) {
        perror("Erro ao abrir o arquivo");
        return 1;
    }

    char linha[MAX_LINE];
    fgets(linha, MAX_LINE, f); // pula cabeçalho
    
    Show registros[1000];
    int count = 0;

    while (fgets(linha, MAX_LINE, f)) {
        linha[strcspn(linha, "\n")] = 0;
        registros[count++] = ler(linha);
    }

    fclose(f);

    char entrada[100];
    while (1) {
        scanf("%s", entrada);
        if (strcmp(entrada, "FIM") == 0) break;

        int encontrado = 0;
        for (int i = 0; i < count; i++) {
            if (strcmp(registros[i].show_id, entrada) == 0) {
                imprimir(registros[i]);
                encontrado = 1;
                break;
            }
        }

        if (!encontrado) {
            Show vazio;
            strcpy(vazio.show_id, "NaN");
            strcpy(vazio.type, "NaN");
            strcpy(vazio.title, "NaN");
            strcpy(vazio.director, "NaN");
            strcpy(vazio.cast[0], "NaN");
            vazio.cast_count = 1;
            strcpy(vazio.country, "NaN");
            strcpy(vazio.date_added, "NaN");
            vazio.release_year = -1;
            strcpy(vazio.rating, "NaN");
            strcpy(vazio.duration, "NaN");
            strcpy(vazio.listed_in[0], "NaN");
            vazio.listed_count = 1;
            imprimir(vazio);
        }
    }

    return 0;
}