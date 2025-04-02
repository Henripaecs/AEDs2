#include <stdio.h>
#include <ctype.h>
#include <string.h>

int contar_maiusculas(const char *str, int i) {
    if (str[i] == '\0') {
        return 0;
    }

    if (isupper(str[i])) {
        return 1 + contar_maiusculas(str, i + 1);
    } else {
        return contar_maiusculas(str, i + 1);
    }
}

void ler_e_processar() {
    char texto[100];

    fgets(texto, sizeof(texto), stdin);
    texto[strcspn(texto, "\n")] = '\0';  

    if (strcmp(texto, "FIM") == 0) {
        return;  
    }

    int total = contar_maiusculas(texto, 0);
    printf("%d\n", total);

    ler_e_processar(); 
}

int main() {
    ler_e_processar();
    return 0;
}
