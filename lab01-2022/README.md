# Doubly Linked List


## Instructions

Implement a doubly linked list.

Like an array, a linked list is a simple linear data structure. Several
common data types can be implemented using linked lists, like queues,
stacks, and associative arrays.

A linked list is a collection of data elements called *nodes*. In a
*singly linked list* each node holds a value and a link to the next node.
In a *doubly linked list* each node also holds a link to the previous
node.

You will write an implementation of a doubly linked list. Implement a
Node to hold a value and pointers to the next and previous nodes. Then
implement a List which holds references to the first and last node and
offers an array-like interface for adding and removing items:

* `push` (*insert value at back*);
* `pop` (*remove value at back*);
* `shift` (*remove value at front*).
* `unshift` (*insert value at front*);

If `pop` or `shift` will  be called on an
empty list, they will throw a `IllegalStateException`.

If you want to know more about linked lists, check [Wikipedia](https://en.wikipedia.org/wiki/Linked_list).

This exercise introduces [generics](https://docs.oracle.com/javase/tutorial/java/generics/index.html).
To make the tests pass you need to construct your class such that it accepts any type of input, e.g. `Integer` or `String`.

Generics are useful because they allow you to write more general and reusable code.
The Java [List](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/List.html) and [Map](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Map.html) implementations are both examples of classes that use generics.
By using them you can construct a `List` containing `Integers` or a list containing `Strings` or any other type.

There are a few constraints on the types used in generics.
One of them is that once you've constructed a `List` containing `Integers`, you can't put `Strings` into it.
You have to specify which type you want to put into the class when you construct it, and that instance can then only be used with that type.

For example you could construct a list of `Integers`:

`DoublyLinkedList<Integer> someList = new DoublyLinkedList<>();`

Now `someList` can only contain `Integers`. You could also do:

`DoublyLinkedList<String> someOtherList = new DoublyLinkedList<>()`

Now `someOtherList` can only contain `Strings`.

## Credits

Questo esercizio è stato preso dal sito https://exercism.org
