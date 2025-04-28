#define _XOPEN_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <locale.h>
#include <time.h>

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

void lerShow(Show *s, char *linha) {
    inicializarShow(s);

    char campos[MAX_FIELDS][MAX_STR];
    int qtd_campos = 0;
    splitCSVLine(linha, campos, &qtd_campos);

    if (qtd_campos > 0) strcpy(s->show_id, campos[0]);
    if (qtd_campos > 1) strcpy(s->type, campos[1]);
    if (qtd_campos > 2) strcpy(s->title, campos[2]);
    if (qtd_campos > 3) strcpy(s->director, campos[3]);
    if (qtd_campos > 4) {
        if (strcmp(campos[4], "NaN") != 0) {
            char *token = strtok(campos[4], ";");
            int i = 0;
            while (token != NULL && i < MAX_ARRAY) {
                strcpy(s->cast[i++], token);
                token = strtok(NULL, ";");
            }
            s->cast_size = i;
        }
    }
    if (qtd_campos > 5) strcpy(s->country, campos[5]);
    if (qtd_campos > 6) strcpy(s->date_added, campos[6]);
    if (qtd_campos > 7) s->release_year = (strcmp(campos[7], "NaN") == 0) ? -1 : atoi(campos[7]);
    if (qtd_campos > 8) strcpy(s->rating, campos[8]);
    if (qtd_campos > 9) strcpy(s->duration, campos[9]);
    if (qtd_campos > 10) {
        if (strcmp(campos[10], "NaN") != 0) {
            char *token = strtok(campos[10], ";");
            int i = 0;
            while (token != NULL && i < MAX_ARRAY) {
                strcpy(s->listed_in[i++], token);
                token = strtok(NULL, ";");
            }
            s->listed_in_size = i;
        }
    }
}


void imprimirShow(Show *s) {
    printf("=> %s ## %s ## %s ## %s ## [", s->show_id, s->title, s->type, s->director);
    for (int i = 0; i < s->cast_size; i++) {
        printf("%s", s->cast[i]);
        if (i < s->cast_size - 1) printf(", ");
    }
    printf("] ## %s ## %s ## %d ## %s ## %s ## [", s->country, s->date_added, s->release_year, s->rating, s->duration);
    for (int i = 0; i < s->listed_in_size; i++) {
        printf("%s", s->listed_in[i]);
        if (i < s->listed_in_size - 1) printf(", ");
    }
    printf("] ##\n");
}

int comparacoes = 0;
int movimentacoes = 0;

void selectionSortRec(Show arr[], int n, int index) {
    if (index == n) return;

    int min_index = index;
    for (int i = index + 1; i < n; i++) {
        comparacoes++;
        if (strcmp(arr[i].title, arr[min_index].title) < 0) {
            min_index = i;
        }
    }

    if (min_index != index) {
        Show temp = arr[index];
        arr[index] = arr[min_index];
        arr[min_index] = temp;
        movimentacoes += 3;
    }

    selectionSortRec(arr, n, index + 1);
}

int main() {
    setlocale(LC_ALL, "en_US.UTF-8");

    FILE *fp = fopen("/tmp/disneyplus.csv", "r");
    //FILE *fp = fopen("./disneyplus.csv", "r");

    if (!fp) {
        perror("Erro ao abrir o arquivo");
        return 1;
    }

    char linha[MAX_LINE];
    fgets(linha, MAX_LINE, fp); // ignorar header

    Show shows[1000];
    int totalShows = 0;

    while (fgets(linha, MAX_LINE, fp)) {
        linha[strcspn(linha, "\n")] = '\0';
        lerShow(&shows[totalShows++], linha);
    }
    fclose(fp);

    char entrada[MAX_STR];
    Show selecionados[1000];
    int totalSelecionados = 0;

    while (1) {
        scanf("%s", entrada);
        if (strcmp(entrada, "FIM") == 0) break;

        for (int i = 0; i < totalShows; i++) {
            if (strcmp(shows[i].show_id, entrada) == 0) {
                selecionados[totalSelecionados++] = shows[i];
                break;
            }
        }
    }

    clock_t inicio = clock();

    selectionSortRec(selecionados, totalSelecionados, 0);

    clock_t fim = clock();

    double tempoExecucao = (double)(fim - inicio) * 1000.0 / CLOCKS_PER_SEC;

    for (int i = 0; i < totalSelecionados; i++) {
        imprimirShow(&selecionados[i]);
    }

    FILE *log = fopen("846431_selecaoRecursiva.txt", "w");
    if (log) {
        fprintf(log, "846431\t%d\t%d\t%.2f\n", comparacoes, movimentacoes, tempoExecucao);
        fclose(log);
    }

    return 0;
}
