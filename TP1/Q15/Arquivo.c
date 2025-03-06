#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main() {
    FILE *file;
    double value;
    char input[50];

    file = fopen("numbers.txt", "w");
    if (!file) {
        perror("Erro ao abrir o arquivo para escrita");
        return 1;
    }

    while (1) {
        scanf("%s", input);
        if (strcmp(input, "FIM") == 0) {
            break;
        }
        if (fprintf(file, "%s\n", input) < 0) {
            perror("Erro");
            fclose(file);
            return 1;
        }
    }
    fclose(file);

    file = fopen("numbers.txt", "r");
    if (!file) {
        perror("Erro");
        return 1;
    }

    fseek(file, 0, SEEK_END);
    long pos = ftell(file);
    char ch;
    
    while (pos > 0) {
        fseek(file, --pos, SEEK_SET);
        ch = fgetc(file);
        if (ch == '\n' && pos != 0) {
            fseek(file, pos + 1, SEEK_SET);
            char buffer[50];
            if (fgets(buffer, sizeof(buffer), file)) {
                printf("%s", buffer);
            }
        }
    }
    
    rewind(file);
    char buffer[50];
    if (fgets(buffer, sizeof(buffer), file)) {
        printf("%s", buffer);
    }
    
    fclose(file);
    return 0;
}
