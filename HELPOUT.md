```
Usage: <main class> [options] [command] [command options]
  Options:
    -h, --help

      Default: false
  Commands:
    list      Печать списка книг. Используйте только одну опцию.
      Usage: list [options]
        Options:
          -a, --all
            все книги
            Default: false
          -l, --lend
            одолженные книги
            Default: false
          -p, --present
            книги в наличии
            Default: false

    delete      Удалить книгу из каталога по каталожному номеру.
      Usage: delete [options]
        Options:
        * -c, --cbn
            каталожный номер книги, число большее нуля
            Default: 0

    lend      Одолжить книгу из каталога по каталожному номеру.
      Usage: lend [options]
        Options:
        * -c, --cbn
            каталожный номер книги, число большее нуля
            Default: 0
        * -d, --for-days
            на сколько дней одолжена книга
            Default: 0
        * -w, --whom
            кому одолжена книга, при наличии пробелоа в имени используйте 
            кавычки 

    return      Вернуть ранее одолженную книгу.
      Usage: return [options]
        Options:
        * -c, --cbn
            каталожный номер книги, число большее нуля
            Default: 0

    add      Добавить новую книгу в каталог
      Usage: add [options]
        Options:
        * -a, --author
            автор, или авторы книги, при наличии пробелоа в имени используйте 
            кавычки 
          -i, --isbn
            номер ISBN-10 или ISBN-13
            Default: <empty string>
        * -t, --title
            название книги
          -y, --year
            год издания книги
            Default: 0

    edit      Изменить данные книги в каталоге
      Usage: edit [options]
        Options:
          -a, --author
            автор, или авторы книги, при наличии пробелоа в имени используйте 
            кавычки 
        * -c, --cbn
            каталожный номер книги, число большее нуля
            Default: 0
          -i, --isbn
            номер ISBN-10 или ISBN-13
          -t, --title
            название книги
          -y, --year
            год издания книги
            Default: 0

```

