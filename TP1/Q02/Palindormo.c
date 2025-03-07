#include <stdio.h>
#include <string.h>
#include <stdbool.h>

int main()
{
    char str[1000];
    while (true)
    {
        fgets(str, sizeof(str), stdin);

        str[strcspn(str, "\n")] = 0;

        if (strcmp(str, "FIM") == 0){
            break;
        }else{
            bool resp = true;
            
            for (int i = 0; i < (strlen(str) / 2); i++){
                if (str[i] != str[strlen(str) - i - 1]){
                    resp = false;
                    break;
                }
            }

            if (resp){
                printf("SIM\n");
            }else{
                printf("NAO\n");
            }
        }
    }

    return 0;
}