#include<stdio.h>
#include<string.h>

void combinar(char str1[], char str2[]){
    int maior = 0;

    if (strlen(str1) >= strlen(str2)){
        maior = strlen(str1);
    }else{
        maior = strlen(str2);
    }

    for (int i = 0; i < maior; i++){
        if (i < strlen(str1))printf("%c", str1[i]);
        if (i < strlen(str2))printf("%c", str2[i]);
    }
    printf("\n");

}

int main(){
    char palavra1[100], palavra2[100];

    while (scanf("%s %s", palavra1, palavra2) != EOF){        
        combinar(palavra1, palavra2);
    }  
        
}