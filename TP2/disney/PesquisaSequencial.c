#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define MAX_SHOWS 10000
#define MAX_TITLE_LENGTH 300
#define MAX_LINE_LENGTH 1000

typedef struct {
    char title[MAX_TITLE_LENGTH];
} Show;

void limparQuebraLinha(char *str) {
    str[strcspn(str, "\n")] = 0;
}

int main() {
    Show vetor[MAX_SHOWS];
    char buscar[MAX_SHOWS][MAX_TITLE_LENGTH];
    int n_shows = 0;
    int n_buscar = 0;
    int comparacoes = 0;
    char linha[MAX_LINE_LENGTH];
    clock_t inicio, fim;

    FILE *arquivo = fopen("./disneyplus.csv", "r");
    if (arquivo == NULL) {
        printf("Erro ao abrir o arquivo disneyplus.csv\n");
        return 1;
    }

    fgets(linha, sizeof(linha), arquivo);

    while (fgets(linha, sizeof(linha), arquivo)) {
        limparQuebraLinha(linha);

        char *token;
        int coluna = 0;
        char titulo[MAX_TITLE_LENGTH] = "";

        token = strtok(linha, ",");
        while (token != NULL) {
            if (coluna == 2) {
                strncpy(titulo, token, MAX_TITLE_LENGTH);
                titulo[MAX_TITLE_LENGTH - 1] = '\0';
                break;
            }
            coluna++;
            token = strtok(NULL, ",");
        }

        if (strlen(titulo) > 0) {
            if (titulo[0] == '\"') {
                memmove(titulo, titulo + 1, strlen(titulo));
            }
            if (titulo[strlen(titulo) - 1] == '\"') {
                titulo[strlen(titulo) - 1] = '\0'; 
            }
            strcpy(vetor[n_shows].title, titulo);
            n_shows++;
        }
    }

    fclose(arquivo);

    while (fgets(linha, sizeof(linha), stdin)) {
        limparQuebraLinha(linha);

        if (strcmp(linha, "FIM") == 0) {
            break;
        }
        strcpy(buscar[n_buscar], linha);
        n_buscar++;
    }

    inicio = clock();

    for (int i = 0; i < n_buscar; i++) {
        int encontrado = 0;
        for (int j = 0; j < n_shows; j++) {
            comparacoes++;
            if (strcmp(vetor[j].title, buscar[i]) == 0) {
                encontrado = 1;
                break;
            }
        }
        if (encontrado) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }
    }

    fim = clock();
    double tempo = ((double)(fim - inicio)) / CLOCKS_PER_SEC * 1000.0; 

    //og
    FILE *log = fopen("846431_sequencial.txt", "w"); 
    if (log != NULL) {
        fprintf(log, "846431\t%.2lf\t%d\n", tempo, comparacoes);
        fclose(log);
    } else {
        printf("Erro ao criar o arquivo de log.\n");
    }

    return 0;
}
