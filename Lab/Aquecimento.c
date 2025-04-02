#include<stdio.h>
#include<string.h>
#include<ctype.h>

int upper(char str[]) {
    int cont = 0;
    int n = strlen(str);
    
    for (int i = 0; i < n; i++) {
        if (isupper(str[i])) {
            cont++;
        }
    }
    return cont;
}

int main() {
    char str[200];

    while (1){

        fgets(str, sizeof(str), stdin);

        str[strcspn(str, "\n")] = '\0'; 

        if (strcmp(str, "FIM") == 0) {
            break;
        }

        int contador = upper(str);
        
        printf("%d\n", contador);
    }
}