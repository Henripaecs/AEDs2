#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define MAX_SHOWS 1500
#define MAX_STR 256
#define TAMANHO_PARIAL 10

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
    printf("=> %s ## %s ## %s ## %s ## [%s] ## %s ## %d ## %s ## %s ## %s ##\n",
           s->show_id, s->title, s->type, s->director, s->country,
           s->date_added, s->release_year, s->rating, s->duration, s->listed_in);
}

void normalizar(char *destino, const char *origem) {
    int j = 0;
    for (int i = 0; origem[i] != '\0'; i++) {
        if (origem[i] != '"' && origem[i] != ' ') {
            destino[j++] = tolower(origem[i]); // Transforma tudo para minúsculo
        }
    }
    destino[j] = '\0';
}

void insertionSort(Show arr[], int n, int *comparacoes, int *movimentacoes) {
    for (int i = 1; i < n; i++) {
        Show chave = arr[i];
        int j = i - 1;
        
        // Compara o tipo
        char normChaveType[MAX_STR], normArrType[MAX_STR];
        normalizar(normChaveType, chave.type);
        normalizar(normArrType, arr[j].type);

        while (j >= 0 && strcmp(normChaveType, normArrType) < 0) {
            arr[j + 1] = arr[j];
            (*movimentacoes)++;
            j--;
        }
        
        // Caso de empate no tipo, compara pelo título
        if (strcmp(normChaveType, normArrType) == 0) {
            char normChaveTitle[MAX_STR], normArrTitle[MAX_STR];
            normalizar(normChaveTitle, chave.title);
            normalizar(normArrTitle, arr[j].title);
            
            // Ordenação do título em ordem alfabética
            while (j >= 0 && strcmp(normChaveTitle, normArrTitle) < 0) {
                arr[j + 1] = arr[j];
                (*movimentacoes)++;
                j--;
            }
        }
        
        arr[j + 1] = chave;
        (*movimentacoes)++;
        (*comparacoes)++;
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

    // Ordena apenas os primeiros 10 elementos
    insertionSort(lista, TAMANHO_PARIAL, &comparacoes, &movimentacoes);

    clock_t fim = clock();
    double tempoExecucao = (double)(fim - inicio) / CLOCKS_PER_SEC * 1000.0;

    // Exibe apenas os 10 primeiros elementos com diretores corretamente
    for (int i = 0; i < TAMANHO_PARIAL && i < tamLista; i++) {
        // Separa os diretores por vírgula e espaço
        char *diretores = lista[i].director;
        printf("=> %s ## %s ## %s ## %s ## [%s] ## %s ## %d ## %s ## %s ## %s ##\n",
               lista[i].show_id, lista[i].title, lista[i].type, diretores, lista[i].country,
               lista[i].date_added, lista[i].release_year, lista[i].rating, lista[i].duration, lista[i].listed_in);
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
