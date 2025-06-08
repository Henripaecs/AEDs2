#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

#define MAX_STR 256
#define MAX_BASE 1500
#define TAM_FILA 5
#define VALOR_PADRAO "NaN"

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

Show base[MAX_BASE];
int qtdBase = 0;

typedef struct {
    Show array[TAM_FILA];
    int primeiro, ultimo, tamanho;
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

void imprimirMedia(FilaCircular *f) {
    int soma = 0, count = 0;
    for (int i = 0, idx = f->primeiro; i < f->tamanho; i++, idx = (idx + 1) % TAM_FILA) {
        int ano = f->array[idx].release_year;
        if (ano > 0) {
            soma += ano;
            count++;
        }
    }
    int media = (count > 0) ? (int)round((double)soma / count) : 0;
    printf("[Media] %d\n", media);
}

void inserirFila(FilaCircular *f, Show s) {
    if (estaCheia(f)) {
        f->primeiro = (f->primeiro + 1) % TAM_FILA;
        f->tamanho--;
    }
    f->array[f->ultimo] = s;
    f->ultimo = (f->ultimo + 1) % TAM_FILA;
    f->tamanho++;
    imprimirMedia(f);
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
        strcpy(removido.title, VALOR_PADRAO);
    }
    return removido;
}

void preencherCampo(char *dest, const char *valor) {
    if (valor == NULL || strlen(valor) == 0) {
        strcpy(dest, VALOR_PADRAO);
    } else {
        strcpy(dest, valor);
    }
}

void extrairCampos(char *linha, char campos[][MAX_STR]) {
    int campo = 0, i = 0, j = 0, entreAspas = 0;
    while (linha[i] != '\0' && campo < 11) {
        if (linha[i] == '"') {
            entreAspas = !entreAspas;
            i++;
        } else if (linha[i] == ',' && !entreAspas) {
            campos[campo][j] = '\0';
            campo++; j = 0; i++;
        } else {
            campos[campo][j++] = linha[i++];
        }
    }
    campos[campo][j] = '\0';
}

void lerShow(Show *reg, char *linha) {
    char campos[11][MAX_STR] = {0};
    extrairCampos(linha, campos);

    preencherCampo(reg->show_id, campos[0]);
    preencherCampo(reg->type, campos[1]);
    preencherCampo(reg->title, campos[2]);
    preencherCampo(reg->director, campos[3]);
    preencherCampo(reg->cast, campos[4]);
    preencherCampo(reg->country, campos[5]);
    preencherCampo(reg->date_added, campos[6]);
    reg->release_year = (strlen(campos[7]) > 0) ? atoi(campos[7]) : -1;
    preencherCampo(reg->rating, campos[8]);
    preencherCampo(reg->duration, campos[9]);
    preencherCampo(reg->listed_in, campos[10]);
}

Show buscarShow(char *id) {
    for (int i = 0; i < qtdBase; i++) {
        if (strcmp(base[i].show_id, id) == 0)
            return base[i];
    }
    Show vazio = {VALOR_PADRAO, VALOR_PADRAO, VALOR_PADRAO, VALOR_PADRAO, VALOR_PADRAO, VALOR_PADRAO, VALOR_PADRAO, -1, VALOR_PADRAO, VALOR_PADRAO, VALOR_PADRAO};
    return vazio;
}

void imprimirShowFormatado(Show *s, int idx) {
    printf("[%d] => %s ## %s ## %s ## %s ## [%s] ## %s ## %d ## %s ## %s ## [%s] ##\n",
           idx, s->show_id, s->title, s->type, s->director, s->cast, s->country,
           s->release_year, s->rating, s->duration, s->listed_in);
}

int main() {
    FILE *fp = fopen("/tmp/disneyplus.csv", "r");
    if (!fp) return 1;

    char linha[4096];
    fgets(linha, sizeof(linha), fp);
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
        if (strcmp(s.title, VALOR_PADRAO) != 0) {
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
            if (strcmp(s.title, VALOR_PADRAO) != 0) {
                inserirFila(&fila, s);
            }
        } else if (comando[0] == 'R') {
            removerFila(&fila, 1);
        }
    }

    for (int i = 0, idx = fila.primeiro; i < fila.tamanho; i++, idx = (idx + 1) % TAM_FILA) {
        imprimirShowFormatado(&fila.array[idx], i);
    }

    return 0;
}
