#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

#define MAX_STR 256
#define TAM_FILA 5

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

Show base[1500];
int qtdBase = 0;

typedef struct {
    Show array[TAM_FILA];
    int primeiro;
    int ultimo;
    int tamanho;
} FilaCircular;

void iniciarFila(FilaCircular *f) {
    f->primeiro = 0;
    f->ultimo = 0;
    f->tamanho = 0;
}

int estaCheia(FilaCircular *f) {
    return f->tamanho == TAM_FILA;
}

int estaVazia(FilaCircular *f) {
    return f->tamanho == 0;
}

Show removerFila(FilaCircular *f, int imprimir) {
    Show removido;
    if (!estaVazia(f)) {
        removido = f->array[f->primeiro];
        f->primeiro = (f->primeiro + 1) % TAM_FILA;
        f->tamanho--;
        if (imprimir) {
            printf("(R) %s\n", removido.title);
        }
    } else {
        strcpy(removido.title, "NaN");
    }
    return removido;
}

void imprimirMedia(FilaCircular *f) {
    int soma = 0, count = 0;
    for (int i = 0, idx = f->primeiro; i < f->tamanho; i++, idx = (idx + 1) % TAM_FILA) {
        if (f->array[idx].release_year > 0) {
            soma += f->array[idx].release_year;
            count++;
        }
    }
    int media = (count > 0) ? (int)floor((double)soma / count) : 0;
    printf("[Media] %d\n", media);
}

void inserirFila(FilaCircular *f, Show s) {
    if (estaCheia(f)) {
        removerFila(f, 0);
    }
    f->array[f->ultimo] = s;
    f->ultimo = (f->ultimo + 1) % TAM_FILA;
    f->tamanho++;
    imprimirMedia(f);
}

int comparar(const void *a, const void *b) {
    const char *pa = *(const char **)a;
    const char *pb = *(const char **)b;
    return strcmp(pa, pb);
}

void ordenarTokens(char *campo) {
    char *itens[50];
    int total = 0;
    char buffer[MAX_STR];
    strcpy(buffer, campo);

    char *token = strtok(buffer, ",");
    while (token && total < 50) {
        while (*token == ' ') token++; // remove espaços à esquerda
        itens[total++] = strdup(token);
        token = strtok(NULL, ",");
    }

    qsort(itens, total, sizeof(char *), comparar);

    campo[0] = '\0';
    for (int i = 0; i < total; i++) {
        strcat(campo, itens[i]);
        if (i < total - 1) strcat(campo, ", ");
        free(itens[i]);
    }
}

void ordenarcast(char *campo) {
    ordenarTokens(campo);
}

void ordenarCategorias(char *campo) {
    ordenarTokens(campo);
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

Show buscarShow(char *id) {
    for (int i = 0; i < qtdBase; i++) {
        if (strcmp(base[i].show_id, id) == 0)
            return base[i];
    }
    Show vazio = {"NaN", "NaN", "NaN", "NaN", "NaN", "NaN", "NaN", -1, "NaN", "NaN", "NaN"};
    return vazio;
}

int main() {
    FILE *fp = fopen("/tmp/disneyplus.csv", "r");
    if (!fp) return 1;

    char linha[4096];
    fgets(linha, sizeof(linha), fp); // pula o cabeçalho

    while (fgets(linha, sizeof(linha), fp)) {
        linha[strcspn(linha, "\n")] = 0;
        lerShow(&base[qtdBase++], linha);
    }
    fclose(fp);

    FilaCircular fila;
    iniciarFila(&fila);

    char entrada[MAX_STR];
    while (fgets(entrada, sizeof(entrada), stdin)) {
        entrada[strcspn(entrada, "\n")] = 0;
        if (strcmp(entrada, "FIM") == 0) break;

        Show s = buscarShow(entrada);
        if (strcmp(s.title, "NaN") != 0) {
            inserirFila(&fila, s);
        }
    }

    int n;
    scanf("%d\n", &n);
    for (int i = 0; i < n; i++) {
        char comando[MAX_STR];
        fgets(comando, sizeof(comando), stdin);
        comando[strcspn(comando, "\n")] = 0;

        if (comando[0] == 'I') {
            char id[20];
            sscanf(comando, "I %s", id);
            Show s = buscarShow(id);
            if (strcmp(s.title, "NaN") != 0) {
                inserirFila(&fila, s);
            }
        } else if (comando[0] == 'R') {
            removerFila(&fila, 1);
        }
    }

    for (int i = 0, idx = fila.primeiro; i < fila.tamanho; i++, idx = (idx + 1) % TAM_FILA) {
        Show *s = &fila.array[idx];
        printf("[%d] => %s ## %s ## %s ## %s ## [%s] ## %s ## %s ## %d ## %s ## %s ## [%s] ##\n",
               i, s->show_id, s->title, s->type, s->director, s->cast, s->country,
               s->date_added, s->release_year, s->rating, s->duration, s->listed_in);
    }

    return 0;
}
