# Console UI test plan

This file is the source of truth for manual-style console UI regression tests. The runner starts a new application process for each test case and compares its standard output exactly. Keep visible prompts, separators, and blank lines in the expected output.

## Program command

```sh
javac -d out src/main/java/*.java && java -cp out doe
```

## Test case: Exit from the main menu

### Aim

Verify that the application displays its main menu and exits politely when the user enters `bye`.

### Inputs

```text
bye
```

### Expected output

```text
____________________________________________________________
     _          
  __| | ___  ___
 / _` |/ _ \ / _ \
| (_| | (_) |  __/
 \__,_|\___/ \___|

hello! i'm doe :).
what can i do for you?
____________________________________________________________
1. neigh
2. meow
3. list
4. todo
____________________________________________________________
type "bye" to exit
____________________________________________________________

____________________________________________________________
bye. hope to see you again soon!
____________________________________________________________

```
