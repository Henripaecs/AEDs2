#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_LINE 100

void escreverArquivo() {
    FILE *file = fopen("numeros.txt", "w");
    if (file == NULL) {
        exit(EXIT_FAILURE);
    }

    char input[MAX_LINE];
    while (1) {
        scanf("%s", input);
        if (strcmp(input, "FIM") == 0) break;

        char *endptr;
        double value = strtod(input, &endptr);

        if (*endptr == '\0') {
            fprintf(file, "%.2lf\n", value);
        } else {
            fprintf(stderr, "Entrada inválida.\n");
        }
    }

    fclose(file);
}

void lerArquivoReverso() {
    FILE *file = fopen("numeros.txt", "r");
    if (file == NULL) {
        exit(EXIT_FAILURE);
    }

    fseek(file, 0, SEEK_END);
    long pos = ftell(file);
    char buffer[MAX_LINE];

    while (pos > 0) {
        fseek(file, --pos, SEEK_SET);
        if (fgetc(file) == '\n') {
            if (fgets(buffer, MAX_LINE, file) != NULL) {
                printf("%s", buffer);
            }
        }
    }

    rewind(file);
    if (fgets(buffer, MAX_LINE, file) != NULL) {
        printf("%s", buffer);
    }

    fclose(file);
}

int main() {
    escreverArquivo();
    lerArquivoReverso();
    return 0;
}