#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <locale.h>

#define MAX_SHOWS 1500
#define MAX_STR 256
#define MAX_PARTIAL_SORT 10  // Limitando para 10 primeiros elementos

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
    int campoIndex = 0;
    int entreAspas = 0;
    char *ptr = linha;
    char buffer[MAX_STR];
    int bufIndex = 0;

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

time_t parseDate(const char *data) {
    if (strcmp(data, "NaN") == 0) return 0;

    struct tm tm = {0};
    setlocale(LC_TIME, "C"); // meses em inglês

    if (strptime(data, "%B %d, %Y", &tm) == NULL) {
        return 0; // falha ao converter
    }
    return mktime(&tm);
}

// Função de comparação adaptada
int compararShows(const Show *a, const Show *b) {
    // Comparar diretores
    int cmpDirector = strcasecmp(a->director, b->director);
    if (cmpDirector != 0) return cmpDirector;

    // Se diretores forem iguais, comparar títulos
    return strcasecmp(a->title, b->title);
}

void trocar(Show *a, Show *b) {
    Show temp = *a;
    *a = *b;
    *b = temp;
}

// Função para realizar o Heap Sort apenas nos 10 primeiros elementos
void heapify(Show arr[], int n, int i, int *comparacoes, int *movimentacoes) {
    int largest = i;
    int left = 2 * i + 1;
    int right = 2 * i + 2;

    if (left < n && compararShows(&arr[left], &arr[largest]) > 0) {
        largest = left;
    }

    if (right < n && compararShows(&arr[right], &arr[largest]) > 0) {
        largest = right;
    }

    if (largest != i) {
        trocar(&arr[i], &arr[largest]);
        (*movimentacoes) += 3;

        heapify(arr, n, largest, comparacoes, movimentacoes);
    }
}

void heapSort(Show arr[], int n, int *comparacoes, int *movimentacoes) {
    // Construir o heap
    for (int i = n / 2 - 1; i >= 0; i--) {
        heapify(arr, n, i, comparacoes, movimentacoes);
    }

    // Extrair elementos do heap
    for (int i = n - 1; i > 0; i--) {
        trocar(&arr[0], &arr[i]);
        (*movimentacoes) += 3;
        heapify(arr, i, 0, comparacoes, movimentacoes);
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

    fgets(linha, sizeof(linha), arquivo); // cabeçalho
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

    // Ordenar por diretor e título antes de aplicar o HeapSort
    for (int i = 0; i < tamLista - 1; i++) {
        for (int j = i + 1; j < tamLista; j++) {
            if (compararShows(&lista[i], &lista[j]) > 0) {
                trocar(&lista[i], &lista[j]);
                movimentacoes += 3;
            }
        }
    }

    // Limitando a lista para os 10 primeiros
    int limit = (tamLista > MAX_PARTIAL_SORT) ? MAX_PARTIAL_SORT : tamLista;
    heapSort(lista, limit, &comparacoes, &movimentacoes);

    clock_t fim = clock();
    double tempoExecucao = (double)(fim - inicio) / CLOCKS_PER_SEC * 1000.0;

    for (int i = 0; i < limit; i++) {
        imprimirShow(&lista[i]);
    }

    FILE *log = fopen("846431_heapsort.txt", "w");
    if (log) {
        fprintf(log, "846431\t%d\t%d\t%.2lf\n", comparacoes, movimentacoes, tempoExecucao);
        fclose(log);
    }

    return 0;
}
