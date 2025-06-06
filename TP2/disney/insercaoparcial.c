#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <time.h>

#define MAX_SHOWS 1500
#define MAX_STR 256
#define TAMANHO_PARIAL 10

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

void ordenarcast(char *campo) {
    char *cast[30];
    int total = 0;

    char *token = strtok(campo, ",");
    while (token && total < 30) {
        while (*token == ' ') token++;
        cast[total++] = strdup(token);
        token = strtok(NULL, ",");
    }

    for (int i = 0; i < total - 1; i++) {
        for (int j = i + 1; j < total; j++) {
            if (strcmp(cast[i], cast[j]) > 0) {
                char *tmp = cast[i];
                cast[i] = cast[j];
                cast[j] = tmp;
            }
        }
    }

    campo[0] = '\0';
    for (int i = 0; i < total; i++) {
        strcat(campo, cast[i]);
        if (i < total - 1) strcat(campo, ", ");
        free(cast[i]);
    }
}

void ordenarCategorias(char *campo) {
    char *categorias[30];
    int total = 0;

    char *token = strtok(campo, ",");
    while (token && total < 30) {
        while (*token == ' ') token++;
        categorias[total++] = strdup(token);
        token = strtok(NULL, ",");
    }

    for (int i = 0; i < total - 1; i++) {
        for (int j = i + 1; j < total; j++) {
            if (strcmp(categorias[i], categorias[j]) > 0) {
                char *tmp = categorias[i];
                categorias[i] = categorias[j];
                categorias[j] = tmp;
            }
        }
    }

    campo[0] = '\0';
    for (int i = 0; i < total; i++) {
        strcat(campo, categorias[i]);
        if (i < total - 1) strcat(campo, ", ");
        free(categorias[i]);
    }
}

void lerShow(Show *s, char *linha) {
    char *campos[11];
    int campoIndex = 0;
    int entreAspas = 0;
    char buffer[MAX_STR];
    int bufIndex = 0;
    char *ptr = linha;

    while (*ptr != '\0' && campoIndex < 11) {
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
    if (campoIndex < 11) {
        campos[campoIndex++] = strdup(buffer);
    }

    if (campoIndex != 11) {
        fprintf(stderr, "Erro: linha com %d campos (esperado: 11):\n%s\n", campoIndex, linha);
        for (int i = 0; i < campoIndex; i++) free(campos[i]);
        memset(s, 0, sizeof(Show));
        return;
    }

    strcpy(s->show_id, campos[0]);
    strcpy(s->type, campos[1]);
    strcpy(s->title, campos[2]);
    strcpy(s->director, campos[3]);
    strcpy(s->cast, campos[4]);
    ordenarcast(s->cast);
    strcpy(s->country, campos[5]);
    strcpy(s->date_added, campos[6]);
    s->release_year = atoi(campos[7]);
    strcpy(s->rating, campos[8]);
    strcpy(s->duration, campos[9]);
    strcpy(s->listed_in, campos[10]);
    ordenarCategorias(s->listed_in);

    for (int j = 0; j < 11; j++) {
        free(campos[j]);
    }
}

void imprimirShow(Show *s) {
    printf("=> %s ## %s ## %s ## %s ## [%s] ## %s ## %s ## %d ## %s ## %s ## [%s] ##\n",
           s->show_id,      // ID
           s->title,        // Título
           s->type,         // Tipo
           s->director,     // Diretores
           s->cast,         // Elenco (ordenado)
           s->country,      // País (ordenado)
           s->date_added,   // Data de adição
           s->release_year, // Ano
           s->rating,       // Classificação
           s->duration,     // Duração
           s->listed_in     // Categorias (ordenadas)
    );
}

void normalizar(char *destino, const char *origem) {
    int j = 0;
    for (int i = 0; origem[i] != '\0'; i++) {
        if (origem[i] != '"') {
            destino[j++] = tolower(origem[i]);
        }
    }
    destino[j] = '\0';
}

void insertionSort(Show arr[], int n, int *comparacoes, int *movimentacoes) {
    for (int i = 1; i < n; i++) {
        Show chave = arr[i];
        int j = i - 1;

        while (j >= 0) {
            char normChaveType[MAX_STR], normArrType[MAX_STR];
            char normChaveTitle[MAX_STR], normArrTitle[MAX_STR];

            normalizar(normChaveType, chave.type);
            normalizar(normArrType, arr[j].type);
            normalizar(normChaveTitle, chave.title);
            normalizar(normArrTitle, arr[j].title);

            (*comparacoes)++;
            int cmpType = strcmp(normChaveType, normArrType);
            int cmpTitle = strcmp(normChaveTitle, normArrTitle);

            if (cmpType < 0 || (cmpType == 0 && cmpTitle < 0)) {
                arr[j + 1] = arr[j];
                (*movimentacoes)++;
                j--;
            } else {
                break;
            }
        }

        arr[j + 1] = chave;
        (*movimentacoes)++;
    }
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
    FILE *arquivo = fopen("/tmp/disneyplus.csv", "r");
    if (!arquivo) {
        perror("Erro ao abrir o arquivo CSV");
        return 1;
    }

    Show shows[MAX_SHOWS];
    int totalShows = 0;
    char linha[1024];

    fgets(linha, sizeof(linha), arquivo); // pula o cabeçalho

    while (fgets(linha, sizeof(linha), arquivo) && totalShows < MAX_SHOWS) {
        linha[strcspn(linha, "\n")] = 0;
        lerShow(&shows[totalShows], linha);
        if (shows[totalShows].show_id[0] != '\0') {
            totalShows++;
        }
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

    insertionSort(lista, tamLista, &comparacoes, &movimentacoes);

    clock_t fim = clock();
    double tempoExecucao = (double)(fim - inicio) / CLOCKS_PER_SEC * 1000.0;

    for (int i = 0; i < TAMANHO_PARIAL && i < tamLista; i++) {
        imprimirShow(&lista[i]);
    }

    FILE *log = fopen("846431_insertionSort.txt", "w");
    if (log) {
        fprintf(log, "846431\t%d\t%d\t%.2lf\n", comparacoes, movimentacoes, tempoExecucao);
        fclose(log);
    } else {
        perror("Erro ao escrever o arquivo de log");
    }

    fgets(entrada, sizeof(entrada), stdin);
    entrada[strcspn(entrada, "\n")] = 0;

    int comparacoesBin = 0;
    pesquisaBinaria(lista, TAMANHO_PARIAL, entrada, &comparacoesBin);

    return 0;
}
