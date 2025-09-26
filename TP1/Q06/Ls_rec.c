#include <stdio.h>
#include <string.h>
#include <ctype.h>

int FirstRec(char *str, int i) {
    if (i == strlen(str)) return 1; 
    char c = str[i];
    if (!(c == 'a' || c == 'A' || c == 'e' || c == 'E' ||
          c == 'i' || c == 'I' || c == 'o' || c == 'O' ||
          c == 'u' || c == 'U')) {
        return 0;
    }
    return FirstRec(str, i + 1);
}

int SecondRec(char *str, int i) {
    if (i == strlen(str)) return 1;
    char c = str[i];
    if (c == 'a' || c == 'A' || c == 'e' || c == 'E' ||
        c == 'i' || c == 'I' || c == 'o' || c == 'O' ||
        c == 'u' || c == 'U' || isdigit(c)) {
        return 0;
    }
    return SecondRec(str, i + 1);
}

int ThirdRec(char *str, int i) {
    if (i == strlen(str)) return 1;
    char c = str[i];
    if (!isdigit(c)) return 0;
    return ThirdRec(str, i + 1);
}

int FourthRec(char *str, int i, int hasDecimal) {
    if (i == strlen(str)) return 1;
    char c = str[i];

    if (c == '.' || c == ',') {
        if (hasDecimal) return 0; 
        return FourthRec(str, i + 1, 1);
    } else if (!isdigit(c)) {
        return 0;
    }
    return FourthRec(str, i + 1, hasDecimal);
}

int main() {
    char entrada[1000];

    while (1) {
        fgets(entrada, sizeof(entrada), stdin);

        entrada[strcspn(entrada, "\n")] = '\0';

        if (strcmp(entrada, "FIM") == 0) break;

        printf("%s ", FirstRec(entrada, 0) ? "SIM" : "NAO");
        printf("%s ", SecondRec(entrada, 0) ? "SIM" : "NAO");
        printf("%s ", ThirdRec(entrada, 0) ? "SIM" : "NAO");
        printf("%s\n", FourthRec(entrada, 0, 0) ? "SIM" : "NAO");
    }

    return 0;
}
