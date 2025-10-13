#define _XOPEN_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <locale.h>
#include <ctype.h>
#include <string.h>

// --- Constantes e Definições ---
#define MAX_LINE 1024
#define MAX_FIELDS 15
#define MAX_STR 500
#define MAX_ARRAY 50

char* strptime(const char* s, const char* f, struct tm* tm) {
  int i, cont;
  char buf[7];

  cont = 0;
  buf[0] = '\0';

  for (i = 0; f[i] != '\0'; i++) {
    if (f[i] == '%') {
      i++;
      switch (f[i]) {
        case 'b': // Mês abreviado
        case 'h':
          sscanf(s + cont, "%3s", buf);
          cont += 3;
          if (strcasecmp(buf, "Jan") == 0) tm->tm_mon = 0;
          else if (strcasecmp(buf, "Feb") == 0) tm->tm_mon = 1;
          else if (strcasecmp(buf, "Mar") == 0) tm->tm_mon = 2;
          else if (strcasecmp(buf, "Apr") == 0) tm->tm_mon = 3;
          else if (strcasecmp(buf, "May") == 0) tm->tm_mon = 4;
          else if (strcasecmp(buf, "Jun") == 0) tm->tm_mon = 5;
          else if (strcasecmp(buf, "Jul") == 0) tm->tm_mon = 6;
          else if (strcasecmp(buf, "Aug") == 0) tm->tm_mon = 7;
          else if (strcasecmp(buf, "Sep") == 0) tm->tm_mon = 8;
          else if (strcasecmp(buf, "Oct") == 0) tm->tm_mon = 9;
          else if (strcasecmp(buf, "Nov") == 0) tm->tm_mon = 10;
          else if (strcasecmp(buf, "Dec") == 0) tm->tm_mon = 11;
          break;
        case 'd': // Dia do mês
        case 'e':
          sscanf(s + cont, "%d", &tm->tm_mday);
          if (s[cont] == ' ') cont++;
          if (tm->tm_mday < 10) cont++; else cont += 2;
          break;
        case 'Y': // Ano com 4 dígitos
          sscanf(s + cont, "%d", &tm->tm_year);
          tm->tm_year -= 1900;
          cont += 4;
          break;
      }
    } else {
      if (s[cont] == f[i]) cont++;
    }
  }
  return (char*)(s + cont);
}

typedef struct {
    char appID[50];
    char name[200];
    char releaseDate[50];
    char estimatedOwners[50];
    double price;
    char supportedLanguages[MAX_STR];
    int metacriticScore;
    double userScore;
    int achievements;
    char publisher[200];
    char developer[200];
    char categories[MAX_STR];
    char genres[MAX_STR];
    char tags[MAX_STR];
} Game;

void inicializarGame(Game *g) {
    strcpy(g->appID, "NaN");
    strcpy(g->name, "NaN");
    strcpy(g->releaseDate, "NaN");
    strcpy(g->estimatedOwners, "NaN");
    g->price = -1.0;
    strcpy(g->supportedLanguages, "NaN");
    g->metacriticScore = -1;
    g->userScore = -1.0;
    g->achievements = -1;
    strcpy(g->publisher, "NaN");
    strcpy(g->developer, "NaN");
    strcpy(g->categories, "NaN");
    strcpy(g->genres, "NaN");
    strcpy(g->tags, "NaN");
}

void removerAspasDuplas(char *str) {
    char temp[MAX_STR];
    int j = 0;
    for (int i = 0; str[i]; i++) {
        if (str[i] != '"') {
            temp[j++] = str[i];
        }
    }
    temp[j] = '\0';
    strcpy(str, temp);
}

void removerAmbasAspas(char *str) {
    char temp[MAX_STR];
    int j = 0;
    for (int i = 0; str[i]; i++) {
        if (str[i] != '"' && str[i] != '\'') {
            temp[j++] = str[i];
        }
    }
    temp[j] = '\0';
    strcpy(str, temp);
}


int dividirCampoGeral(char *campo, char array[MAX_ARRAY][MAX_STR]) {
    if (campo == NULL || strlen(campo) == 0) {
        strcpy(array[0], "NaN");
        return 1;
    }
    int count = 0;
    char *token = strtok(campo, ",");
    while (token && count < MAX_ARRAY) {
        while (*token == ' ') token++;
        removerAspasDuplas(token); // Usa a remoção de aspas duplas
        strncpy(array[count++], token, MAX_STR - 1);
        array[count-1][MAX_STR - 1] = '\0';
        token = strtok(NULL, ",");
    }
    return count;
}

int dividirCampoParaLinguas(char *campo, char array[MAX_ARRAY][MAX_STR]) {
    if (campo == NULL || strlen(campo) == 0) {
        strcpy(array[0], "NaN");
        return 1;
    }
    int count = 0;
    char *token = strtok(campo, ",");
    while (token && count < MAX_ARRAY) {
        while (*token == ' ') token++;
        removerAmbasAspas(token); // Usa a remoção de ambas as aspas
        strncpy(array[count++], token, MAX_STR - 1);
        array[count-1][MAX_STR - 1] = '\0';
        token = strtok(NULL, ",");
    }
    return count;
}

void imprimirLista(const char* campo, int comColchetes, int ehLingua) {
    char tempStr[MAX_STR];
    strncpy(tempStr, campo, MAX_STR);
    tempStr[MAX_STR - 1] = '\0';

    char lista[MAX_ARRAY][MAX_STR];
    int size;

    if (ehLingua) {
        size = dividirCampoParaLinguas(tempStr, lista);
    } else {
        size = dividirCampoGeral(tempStr, lista);
    }

    if (comColchetes) printf("[");

    if (size == 1 && strcmp(lista[0], "NaN") == 0) {
    } else {
        for (int i = 0; i < size; i++) {
            printf("%s", lista[i]);
            if (i < size - 1) printf(", ");
        }
    }
    
    if (comColchetes) printf("]");
}

void imprimirGame(Game *g) {
    char dataFormatada[50] = "NaN";
    if (strcmp(g->releaseDate, "NaN") != 0 && strlen(g->releaseDate) > 0) {
        struct tm tm = {0};
        int temDia = 0;

        if (strptime(g->releaseDate, "%b %d, %Y", &tm) != NULL) {
            temDia = 1;
        } else {
            strptime(g->releaseDate, "%b, %Y", &tm);
        }

        setlocale(LC_TIME, "pt_BR.UTF-8");

        if (temDia) {
            strftime(dataFormatada, sizeof(dataFormatada), "%d/%m/%Y", &tm);
        } else {
            strftime(dataFormatada, sizeof(dataFormatada), "%B de %Y", &tm);
        }
    }

    printf("=> %s ## %s ## %s ## %s ## ", g->appID, g->name, dataFormatada, g->estimatedOwners);
    
    if (g->price == 0.0) {
        printf("%.1f", g->price);
    } else {
        printf("%.2f", g->price);
    }

    printf(" ## ");
    
    imprimirLista(g->supportedLanguages, 0, 1);
    printf(" ## %d ## %.1f ## %d ## ", g->metacriticScore, g->userScore, g->achievements);
    
    imprimirLista(g->publisher, 1, 0);
    printf(" ## ");
    imprimirLista(g->developer, 1, 0);
    printf(" ## ");
    imprimirLista(g->categories, 1, 0);
    printf(" ## ");
    imprimirLista(g->genres, 1, 0);
    printf(" ## ");
    imprimirLista(g->tags, 1, 0);
    printf(" ##\n");
}

int parseCSVLine(const char *linha, char campos[MAX_FIELDS][MAX_STR]) {
    int i = 0, inQuotes = 0, col = 0, ci = 0;
    char ch;
    while ((ch = linha[i++]) != '\0' && col < MAX_FIELDS) {
        if (ch == '"') {
            inQuotes = !inQuotes;
        } else if (ch == ',' && !inQuotes) {
            campos[col][ci] = '\0';
            col++;
            ci = 0;
        } else {
            if (ci < MAX_STR - 1) {
                campos[col][ci++] = ch;
            }
        }
    }
    campos[col][ci] = '\0';
    return col + 1;
}

int buscarPorID(const char *idBuscado, const char *arquivoCSV, Game *g) {
    FILE *file = fopen(arquivoCSV, "r");
    if (!file) {
        perror("Erro ao abrir o arquivo");
        return 0;
    }

    char linha[MAX_LINE];
    fgets(linha, sizeof(linha), file);

    while (fgets(linha, sizeof(linha), file)) {
        linha[strcspn(linha, "\r\n")] = '\0';
        char campos[MAX_FIELDS][MAX_STR] = {0};
        parseCSVLine(linha, campos);
        
        if (strcmp(campos[0], idBuscado) != 0) continue;

        inicializarGame(g);
        strcpy(g->appID, campos[0]);
        strcpy(g->name, campos[1]);
        strcpy(g->releaseDate, campos[2]);
        strcpy(g->estimatedOwners, campos[3]);
        g->price = strlen(campos[4]) > 0 ? atof(campos[4]) : -1.0;
        strcpy(g->supportedLanguages, campos[5]);
        g->metacriticScore = strlen(campos[6]) > 0 ? atoi(campos[6]) : -1;
        g->userScore = strlen(campos[7]) > 0 ? atof(campos[7]) : -1.0;
        g->achievements = strlen(campos[8]) > 0 ? atoi(campos[8]) : -1;
        strcpy(g->publisher, campos[9]);
        strcpy(g->developer, campos[10]);
        strcpy(g->categories, campos[11]);
        strcpy(g->genres, campos[12]);
        strcpy(g->tags, campos[13]);

        fclose(file);
        return 1;
    }

    fclose(file);
    return 0;
}

int main() {
    char entrada[MAX_STR];
    const char *arquivo = "/tmp/games.csv";
    // const char *arquivo = "./games.csv";

    while (fgets(entrada, sizeof(entrada), stdin)) {
        entrada[strcspn(entrada, "\n")] = '\0';
        if (strcmp(entrada, "FIM") == 0) break;

        Game g;
        if (buscarPorID(entrada, arquivo, &g)) {
            imprimirGame(&g);
        } else {
            printf("=> NaN ## NaN ## NaN ## NaN ## -1.00 ## NaN ## -1 ## -1.00 ## -1 ## [NaN] ## [NaN] ## [NaN] ## [NaN] ## [NaN] ##\n");
        }
    }

    return 0;
}