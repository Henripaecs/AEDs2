#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define MAX_SHOWS 1000
#define MAX_STR 256

typedef struct {
    char show_id[MAX_STR];
    char type[MAX_STR];
    char title[MAX_STR];
    char director[MAX_STR];
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
    int i = 0;
    int entreAspas = 0;
    char *ptr = linha;
    char buffer[MAX_STR];
    int bufIndex = 0;

    while (*ptr != '\0' && campoIndex < 12) {
        if (*ptr == '"') {
            entreAspas = !entreAspas; // toggle
        } else if (*ptr == ',' && !entreAspas) {
            buffer[bufIndex] = '\0';
            campos[campoIndex] = strdup(buffer);
            campoIndex++;
            bufIndex = 0;
        } else {
            buffer[bufIndex++] = *ptr;
        }
        ptr++;
    }

    buffer[bufIndex] = '\0';
    campos[campoIndex] = strdup(buffer); // último campo

    // preenche os campos do Show
    strcpy(s->show_id, campos[0]);
    strcpy(s->type, campos[1]);
    strcpy(s->title, campos[2]);
    strcpy(s->director, campos[3]);
    strcpy(s->country, campos[4]);
    strcpy(s->date_added, campos[5]);
    s->release_year = atoi(campos[6]);
    strcpy(s->rating, campos[7]);
    strcpy(s->duration, campos[8]);
    strcpy(s->listed_in, campos[9]);
    
    for (int j = 0; j <= campoIndex; j++) {
        free(campos[j]);
    }
}


void imprimirShow(Show *s) {
    printf("=> %s ## %s ## %s ## %s ## %s ## %s ## %d ## %s ## %s ## %s ##\n",
           s->show_id, s->title, s->type, s->director, s->country,
           s->date_added, s->release_year, s->rating, s->duration, s->listed_in);
}

void normalizar(char *destino, const char *origem) {
    int j = 0;
    for (int i = 0; origem[i] != '\0'; i++) {
        if (origem[i] != '"' && origem[i] != ' ') {
            destino[j++] = tolower(origem[i]);
        }
    }
    destino[j] = '\0';
}

void selectionSortRecursivo(Show arr[], int n, int i, int *comparacoes, int *movimentacoes) {
    if (i >= n - 1) return;

    int menor = i;
    for (int j = i + 1; j < n; j++) {
        (*comparacoes)++;

        char normJ[MAX_STR], normMenor[MAX_STR];
        normalizar(normJ, arr[j].title);
        normalizar(normMenor, arr[menor].title);

        if (strcmp(normJ, normMenor) < 0) {
            menor = j;
        }
    }

    if (menor != i) {
        Show temp = arr[i];
        arr[i] = arr[menor];
        arr[menor] = temp;
        (*movimentacoes) += 3;
    }

    selectionSortRecursivo(arr, n, i + 1, comparacoes, movimentacoes);
}

int pesquisaBinaria(Show arr[], int n, char *titulo, int *comparacoes) {
    int esq = 0, dir = n - 1;
    while (esq <= dir) {
        int meio = (esq + dir) / 2;
        (*comparacoes)++;
        int cmp = strcmp(arr[meio].title, titulo);
        if (cmp == 0)
            return meio;
        else if (cmp < 0)
            esq = meio + 1;
        else
            dir = meio - 1;
    }
    return -1;
}

int main() {
    FILE *arquivo = fopen("/tmp/disneyplus.csv", "r");//verde
    //FILE *arquivo = fopen("./disneyplus.csv", "r");//marquina
    if (!arquivo) {
        perror("Erro ao abrir o arquivo CSV");
        return 1;
    }

    Show shows[MAX_SHOWS];
    int totalShows = 0;
    char linha[1024];

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

    while (1) {
        fgets(entrada, sizeof(entrada), stdin);
        entrada[strcspn(entrada, "\n")] = 0;
        if (strcmp(entrada, "FIM") == 0)
            break;

        for (int i = 0; i < totalShows; i++) {
            if (strcmp(shows[i].show_id, entrada) == 0) {
                lista[tamLista++] = shows[i];
                break;
            }
        }
    }

    int comparacoes = 0, movimentacoes = 0;
    clock_t inicio = clock();

    selectionSortRecursivo(lista, tamLista, 0, &comparacoes, &movimentacoes);

    clock_t fim = clock();
    double tempoExecucao = (double)(fim - inicio) / CLOCKS_PER_SEC * 1000.0;

    for (int i = 0; i < tamLista; i++) {
        imprimirShow(&lista[i]);
    }

    FILE *log = fopen("846431_selecaoRecursiva.txt", "w");
    if (log) {
        fprintf(log, "846431\t%d\t%d\t%.2lf\n", comparacoes, movimentacoes, tempoExecucao);
        fclose(log);
    } else {
        perror("Erro ao escrever o arquivo de log");
    }

    fgets(entrada, sizeof(entrada), stdin);
    entrada[strcspn(entrada, "\n")] = 0;

    int comparacoesBin = 0;
    pesquisaBinaria(lista, tamLista, entrada, &comparacoesBin);


    return 0;
}
