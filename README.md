# Fruit Shopping Store

## Instructions 

To run the program from the command line, extract the tar/zip file from the build/distributions folder.
Then run the appropriate script for your operating system in the bin folder:

You pass in Items you would like to buy as program arguments (see the example below):

1. Here the code has a multi-buy dicount for 3 Apples, but the fourth one is charged full price.
2. Carrots also have a percentatege discount, but Oranges and Banana's are fully priced.

```shell
./code-excercise Apple Apple Apple Carrot Banana
```

## Output 

The program will then apply Multibuy Discounts to some Item Sku's and Percentage discounts to others,  
and print a receipt at the end,and whether the item was part of a promotion:

Receipt
------------------------------
Apple  | 13.00 |MultiBuyPromotion |
------------------------------
Apple  | 13.00 |MultiBuyPromotion |
------------------------------
Apple  | 13.00 |MultiBuyPromotion |
------------------------------
Apple  | 20.00 |No Promotion |
------------------------------
Orange  | 15.00 |No Promotion |
------------------------------
Carrot  | 42.50 |PercentDiscountPromotion |
------------------------------
Banana  | 30.00 |No Promotion |
------------------------------
Price Total: £146.50
______________________________
