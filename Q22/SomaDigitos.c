#include<stdio.h>
#include<string.h>
#include<stdlib.h>


int soma(int n){
    if (n == 0){
        return 0;
    }
    return (n % 10) + soma(n / 10);
}


int main(){
    char num[30];

    while(1){
        scanf("%s", num);

        if(strcmp(num, "FIM") == 0){
            break;
        }
        int numero = atoi(num);

        printf("%d\n", soma(numero));
    }
    return 0;
}