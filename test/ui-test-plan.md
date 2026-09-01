# Console UI test plan

This file is the source of truth for manual-style console UI regression tests. The runner starts a new application process for each test case and compares its standard output exactly. Keep visible prompts, separators, and blank lines in the expected output.

## Program command

```sh
./gradlew --quiet runConsole
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

## Test case: Exit nested todo menus

### Aim

Verify that `exit` from task-type selection returns to the todo menu, and `exit` from the todo menu returns to the main menu.

### Inputs

```text
todo
add
exit
exit
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
modify todo list
1. add
2. remove
3. view
4. mark
5. unmark
6. find
7. exit
____________________________________________________________

____________________________________________________________
what type of task would you like to add?
1. todo
2. deadline
3. event
4. exit
____________________________________________________________

____________________________________________________________
modify todo list
1. add
2. remove
3. view
4. mark
5. unmark
6. find
7. exit
____________________________________________________________

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
