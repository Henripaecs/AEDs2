#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MAX_SHOWS 1500
#define MAX_STR 256

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

// Lista de ponteiros
Show* lista[MAX_SHOWS];
int n = 0;

void substituirPorNaN(char *str) {
    if (str[0] == '\0') {
        strcpy(str, "NaN");
    }
}

void inserirInicio(Show* show) {
    for (int i = n; i > 0; i--) {
        lista[i] = lista[i - 1];
    }
    lista[0] = show;
    n++;
}

void inserir(Show* show, int pos) {
    for (int i = n; i > pos; i--) {
        lista[i] = lista[i - 1];
    }
    lista[pos] = show;
    n++;
}

void inserirFim(Show* show) {
    lista[n++] = show;
}

Show* removerInicio() {
    Show* removido = lista[0];
    for (int i = 0; i < n - 1; i++) {
        lista[i] = lista[i + 1];
    }
    n--;
    return removido;
}

Show* remover(int pos) {
    Show* removido = lista[pos];
    for (int i = pos; i < n - 1; i++) {
        lista[i] = lista[i + 1];
    }
    n--;
    return removido;
}

Show* removerFim() {
    return lista[--n];
}

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
        memset(s, 0, sizeof(Show));
        for (int i = 0; i < campoIndex; i++) free(campos[i]);
        return;
    }

    strcpy(s->show_id, campos[0]);        substituirPorNaN(s->show_id);
    strcpy(s->type, campos[1]);           substituirPorNaN(s->type);
    strcpy(s->title, campos[2]);          substituirPorNaN(s->title);
    strcpy(s->director, campos[3]);       substituirPorNaN(s->director);
    strcpy(s->cast, campos[4]);           substituirPorNaN(s->cast); ordenarcast(s->cast);
    strcpy(s->country, campos[5]);        substituirPorNaN(s->country);
    strcpy(s->date_added, campos[6]);     substituirPorNaN(s->date_added);
    s->release_year = atoi(campos[7]);    if (campos[7][0] == '\0') s->release_year = 0;
    strcpy(s->rating, campos[8]);         substituirPorNaN(s->rating);
    strcpy(s->duration, campos[9]);       substituirPorNaN(s->duration);
    strcpy(s->listed_in, campos[10]);     substituirPorNaN(s->listed_in); ordenarCategorias(s->listed_in);

    for (int i = 0; i < 11; i++) {
        free(campos[i]);
    }
}

void imprimirShow(Show *s) {
    char ano_str[10];
    if (s->release_year == 0)
        strcpy(ano_str, "NaN");
    else
        sprintf(ano_str, "%d", s->release_year);

    printf("=> %s ## %s ## %s ## %s ## [%s] ## %s ## %s ## %s ## %s ## %s ## [%s] ##\n",
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

    // Parte 1: leitura de IDs até FIM
    char entrada[MAX_STR];
    while (1) {
        fgets(entrada, sizeof(entrada), stdin);
        entrada[strcspn(entrada, "\n")] = 0;
        if (strcmp(entrada, "FIM") == 0)
            break;

        for (int i = 0; i < totalShows; i++) {
            if (strcmp(shows[i].show_id, entrada) == 0) {
                inserirFim(&shows[i]);
                break;
            }
        }
    }

    // Parte 2: comandos
    int qntComandos;
    scanf("%d\n", &qntComandos);
    for (int i = 0; i < qntComandos; i++) {
        char comando[100];
        fgets(comando, sizeof(comando), stdin);
        comando[strcspn(comando, "\n")] = 0;

        if (strncmp(comando, "II ", 3) == 0) {
            char id[MAX_STR];
            sscanf(comando + 3, "%s", id);
            for (int j = 0; j < totalShows; j++) {
                if (strcmp(shows[j].show_id, id) == 0) {
                    inserirInicio(&shows[j]);
                    break;
                }
            }
        } else if (strncmp(comando, "IF ", 3) == 0) {
            char id[MAX_STR];
            sscanf(comando + 3, "%s", id);
            for (int j = 0; j < totalShows; j++) {
                if (strcmp(shows[j].show_id, id) == 0) {
                    inserirFim(&shows[j]);
                    break;
                }
            }
        } else if (strncmp(comando, "I*", 2) == 0) {
            int pos;
            char id[MAX_STR];
            sscanf(comando + 2, "%d %s", &pos, id);
            for (int j = 0; j < totalShows; j++) {
                if (strcmp(shows[j].show_id, id) == 0) {
                    inserir(&shows[j], pos);
                    break;
                }
            }
        } else if (strcmp(comando, "RI") == 0) {
            Show* s = removerInicio();
            if (s) printf("(R) %s\n", s->title);
        } else if (strcmp(comando, "RF") == 0) {
            Show* s = removerFim();
            if (s) printf("(R) %s\n", s->title);
        } else if (strncmp(comando, "R*", 2) == 0) {
            int pos;
            sscanf(comando + 2, "%d", &pos);
            Show* s = remover(pos);
            if (s) printf("(R) %s\n", s->title);
        }
    }

    for (int i = 0; i < n; i++) {
        imprimirShow(lista[i]);
    }

    return 0;
}
