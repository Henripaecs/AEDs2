#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <math.h>

#define MAX_SHOWS 1500
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

Show* fila[TAM_FILA];
int inicio = 0, fim = 0, tamanho = 0;

void substituirPorNaN(char *str) {
    if (str[0] == '\0') {
        strcpy(str, "NaN");
    }
}

void ordenar(char *campo) {
    char *tokens[30];
    int total = 0;
    char *token = strtok(campo, ",");

    while (token && total < 30) {
        while (*token == ' ') token++;
        tokens[total++] = strdup(token);
        token = strtok(NULL, ",");
    }

    for (int i = 0; i < total - 1; i++) {
        for (int j = i + 1; j < total; j++) {
            if (strcmp(tokens[i], tokens[j]) > 0) {
                char *tmp = tokens[i];
                tokens[i] = tokens[j];
                tokens[j] = tmp;
            }
        }
    }

    campo[0] = '\0';
    for (int i = 0; i < total; i++) {
        strcat(campo, tokens[i]);
        if (i < total - 1) strcat(campo, ", ");
        free(tokens[i]);
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
        memset(s, 0, sizeof(Show));
        for (int i = 0; i < campoIndex; i++) free(campos[i]);
        return;
    }

    strcpy(s->show_id, campos[0]);        substituirPorNaN(s->show_id);
    strcpy(s->type, campos[1]);           substituirPorNaN(s->type);
    strcpy(s->title, campos[2]);          substituirPorNaN(s->title);
    strcpy(s->director, campos[3]);       substituirPorNaN(s->director);
    strcpy(s->cast, campos[4]);           substituirPorNaN(s->cast); ordenar(s->cast);
    strcpy(s->country, campos[5]);        substituirPorNaN(s->country);
    strcpy(s->date_added, campos[6]);     substituirPorNaN(s->date_added);
    s->release_year = atoi(campos[7]);    if (campos[7][0] == '\0') s->release_year = 0;
    strcpy(s->rating, campos[8]);         substituirPorNaN(s->rating);
    strcpy(s->duration, campos[9]);       substituirPorNaN(s->duration);
    strcpy(s->listed_in, campos[10]);     substituirPorNaN(s->listed_in); ordenar(s->listed_in);

    for (int i = 0; i < 11; i++) {
        free(campos[i]);
    }
}

void imprimirShow(Show *s, int index) {
    char ano_str[10];
    if (s->release_year == 0)
        strcpy(ano_str, "NaN");
    else
        sprintf(ano_str, "%d", s->release_year);

    printf("[%d] => %s ## %s ## %s ## %s ## [%s] ## %s ## %s ## %s ## %s ## %s ## [%s] ##\n",
        index,
        s->show_id,
        s->title,
        s->type,
        s->director,
        s->cast,
        s->country,
        s->date_added,
        ano_str,
        s->rating,
        s->duration,
        s->listed_in
    );
}

void enfileirar(Show* s) {
    if (tamanho == TAM_FILA) {
        Show* removido = fila[inicio];
        printf("(R) %s\n", removido->title);
        inicio = (inicio + 1) % TAM_FILA;
        tamanho--;
    }
    fila[fim] = s;
    fim = (fim + 1) % TAM_FILA;
    tamanho++;

    // Calcular média
    int soma = 0, cont = 0;
    for (int i = 0, idx = inicio; i < tamanho; i++, idx = (idx + 1) % TAM_FILA) {
        soma += fila[idx]->release_year;
        cont++;
    }

    printf("[Media] %d\n", (int)round((float)soma / cont));
}

int main() {
    FILE *arquivo = fopen("/tmp/disneyplus.csv", "r");
    if (!arquivo) {
        perror("Erro ao abrir o CSV");
        return 1;
    }

    Show shows[MAX_SHOWS];
    int totalShows = 0;
    char linha[1024];

    fgets(linha, sizeof(linha), arquivo); // Cabeçalho

    while (fgets(linha, sizeof(linha), arquivo) && totalShows < MAX_SHOWS) {
        linha[strcspn(linha, "\n")] = 0;
        lerShow(&shows[totalShows], linha);
        if (shows[totalShows].show_id[0] != '\0') {
            totalShows++;
        }
    }

    fclose(arquivo);

    char entrada[MAX_STR];
    while (1) {
        fgets(entrada, sizeof(entrada), stdin);
        entrada[strcspn(entrada, "\n")] = 0;
        if (strcmp(entrada, "FIM") == 0)
            break;

        for (int i = 0; i < totalShows; i++) {
            if (strcmp(shows[i].show_id, entrada) == 0) {
                enfileirar(&shows[i]);
                break;
            }
        }
    }

    for (int i = 0, idx = inicio; i < tamanho; i++, idx = (idx + 1) % TAM_FILA) {
        imprimirShow(fila[idx], i);
    }

    return 0;
}
