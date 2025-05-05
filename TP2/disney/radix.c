#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define MAX_SHOWS 1450
#define MAX_STR 256
#define MAX_DIGITO 10

typedef struct {
    char show_id[MAX_STR];
    char type[MAX_STR];
    char title[MAX_STR];
    char director[MAX_STR];
    char cast[MAX_STR];
    char country[MAX_STR];
    char date_added[MAX_STR];
    int release_year;
    char rating[MAX_STR];
    char duration[MAX_STR];
    char listed_in[MAX_STR];
} Show;

void lerShow(Show *s, char *linha) {
    char *campos[12];
    int campoIndex = 0, entreAspas = 0, bufIndex = 0;
    char buffer[MAX_STR], *ptr = linha;

    while (*ptr != '\0' && campoIndex < 12) {
        if (*ptr == '"') {
            entreAspas = !entreAspas;
        } else if (*ptr == ',' && !entreAspas) {
            buffer[bufIndex] = '\0';
            campos[campoIndex++] = strdup(buffer);
            bufIndex = 0;
        } else {
            buffer[bufIndex++] = *ptr;
        }
        ptr++;
    }
    buffer[bufIndex] = '\0';
    campos[campoIndex] = strdup(buffer);

    strcpy(s->show_id, strlen(campos[0]) ? campos[0] : "NaN");
    strcpy(s->type, strlen(campos[1]) ? campos[1] : "NaN");
    strcpy(s->title, strlen(campos[2]) ? campos[2] : "NaN");
    strcpy(s->director, strlen(campos[3]) ? campos[3] : "NaN");
    strcpy(s->cast, strlen(campos[4]) ? campos[4] : "NaN");
    strcpy(s->country, strlen(campos[5]) ? campos[5] : "NaN");
    strcpy(s->date_added, strlen(campos[6]) ? campos[6] : "NaN");
    s->release_year = strlen(campos[7]) ? atoi(campos[7]) : -1;
    strcpy(s->rating, strlen(campos[8]) ? campos[8] : "NaN");
    strcpy(s->duration, strlen(campos[9]) ? campos[9] : "NaN");
    strcpy(s->listed_in, strlen(campos[10]) ? campos[10] : "NaN");

    for (int j = 0; j <= campoIndex; j++) {
        free(campos[j]);
    }
}

void ordenarCast(char *cast) {
    if (strcmp(cast, "NaN") == 0) return;

    char nomes[MAX_SHOWS][MAX_STR];
    int qtd = 0;

    char *token = strtok(cast, ",");
    while (token != NULL && qtd < MAX_SHOWS) {
        while (*token == ' ') token++;
        strncpy(nomes[qtd++], token, MAX_STR);
        token = strtok(NULL, ",");
    }

    for (int i = 0; i < qtd - 1; i++) {
        for (int j = i + 1; j < qtd; j++) {
            if (strcmp(nomes[i], nomes[j]) > 0) {
                char temp[MAX_STR];
                strcpy(temp, nomes[i]);
                strcpy(nomes[i], nomes[j]);
                strcpy(nomes[j], temp);
            }
        }
    }

    cast[0] = '\0';
    for (int i = 0; i < qtd; i++) {
        strcat(cast, nomes[i]);
        if (i < qtd - 1) strcat(cast, ", ");
    }
}

void imprimirShow(Show *s) {
    char castOrdenado[MAX_STR];
    strcpy(castOrdenado, s->cast);
    ordenarCast(castOrdenado);

    printf("=> %s ## %s ## %s ## %s ## [%s] ## %s ## %s ## %d ## %s ## %s ## [%s] ##\n",
           s->show_id, s->title, s->type, s->director, castOrdenado, s->country,
           s->date_added, s->release_year, s->rating, s->duration, s->listed_in);
}

int getMaxReleaseYear(Show arr[], int n) {
    int max = arr[0].release_year;
    for (int i = 1; i < n; i++) {
        if (arr[i].release_year > max)
            max = arr[i].release_year;
    }
    return max;
}

void countSort(Show arr[], int n, int exp, int *movimentacoes) {
    Show output[n];
    int count[10] = {0};

    for (int i = 0; i < n; i++)
        count[(arr[i].release_year / exp) % 10]++;

    for (int i = 1; i < 10; i++)
        count[i] += count[i - 1];

    for (int i = n - 1; i >= 0; i--) {
        output[count[(arr[i].release_year / exp) % 10] - 1] = arr[i];
        count[(arr[i].release_year / exp) % 10]--;
        (*movimentacoes) += 3;
    }

    for (int i = 0; i < n; i++)
        arr[i] = output[i];
}

void bubbleDesempate(Show arr[], int ini, int fim, int *comparacoes, int *movimentacoes) {
    for (int i = ini; i < fim - 1; i++) {
        for (int j = ini; j < fim - 1 - (i - ini); j++) {
            (*comparacoes)++;
            if (strcasecmp(arr[j].title, arr[j + 1].title) > 0) {
                Show temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
                (*movimentacoes) += 3;
            }
        }
    }
}

void radixSortReleaseYear(Show arr[], int n, int *comparacoes, int *movimentacoes) {
    int max = getMaxReleaseYear(arr, n);
    for (int exp = 1; max / exp > 0; exp *= 10) {
        countSort(arr, n, exp, movimentacoes);
    }

    // Desempatar títulos entre elementos com mesmo release_year
    int i = 0;
    while (i < n) {
        int j = i + 1;
        while (j < n && arr[j].release_year == arr[i].release_year) {
            j++;
        }
        bubbleDesempate(arr, i, j, comparacoes, movimentacoes);
        i = j;
    }
}

int main() {
    FILE *arquivo = fopen("/tmp/disneyplus.csv", "r");
    if (!arquivo) {
        perror("Erro ao abrir o arquivo CSV");
        return 1;
    }

    Show shows[MAX_SHOWS];
    int totalShows = 0;
    char linha[4096];

    fgets(linha, sizeof(linha), arquivo);
    while (fgets(linha, sizeof(linha), arquivo) && totalShows < MAX_SHOWS) {
        linha[strcspn(linha, "\n")] = 0;
        lerShow(&shows[totalShows], linha);
        totalShows++;
    }
    fclose(arquivo);

    Show lista[MAX_SHOWS];
    int tamLista = 0;
    char entrada[MAX_STR];

    while (fgets(entrada, sizeof(entrada), stdin)) {
        entrada[strcspn(entrada, "\n")] = 0;
        if (strcmp(entrada, "FIM") == 0) break;
        for (int i = 0; i < totalShows; i++) {
            if (strcmp(shows[i].show_id, entrada) == 0) {
                lista[tamLista++] = shows[i];
                break;
            }
        }
    }

    int comparacoes = 0, movimentacoes = 0;
    clock_t inicio = clock();

    radixSortReleaseYear(lista, tamLista, &comparacoes, &movimentacoes);

    clock_t fim = clock();
    double tempoExecucao = (double)(fim - inicio) / CLOCKS_PER_SEC * 1000.0;

    for (int i = 0; i < tamLista; i++) {
        imprimirShow(&lista[i]);
    }

    FILE *log = fopen("846431_radixsort.txt", "w");
    if (log) {
        fprintf(log, "846431\t%d\t%d\t%.2lf\n", comparacoes, movimentacoes, tempoExecucao);
        fclose(log);
    }

    return 0;
}
