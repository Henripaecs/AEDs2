#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main() {
    FILE *file;
    double value;
    char num[50];

    file = fopen("numbers.txt", "w");
    if (!file) {
        return 1;
    }

    while (1) {
        scanf("%s", num);
        if (strcmp(num, "FIM") == 0) {
            break;
        }
        if (fprintf(file, "%s\n", num) < 0) {
            fclose(file);
            return 1;
        }
    }
    fclose(file);

    file = fopen("numbers.txt", "r");
    if (!file) {
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
