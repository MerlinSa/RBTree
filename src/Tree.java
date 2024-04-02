


/**
 * todo  Реализуем класс для бинарного поиска:
 * Класс Node для хранения элементов дерева
 * Класс Tree для реализации самого дерева;
 * Реал-м класс без методов.
 */

/**
 * @reference root это корень дерева, начальный нод
 */
public class Tree {
    Node root;


    class Node { //каждый узел хранит в себе значение и ссылки на левый и правый узлы
        int value;
        Node left;
        Node right;
        Color color;

    }

    enum Color { //перечисление цветов
        BLACK,
        RED
    }

    /**
     * todo Реализуем метод добавления в дерево
     * Добавляем метод insert(int value) для вставки числа в дерево.
     * Для определения позиции перед вставкой выполняется поиск того места дерева,
     * где это число могло бы находиться.
     *  БИНАРНОЕ ДЕРЕВО ПОИСКА НЕ ХРАНИТ ПОВТОРЯЮЩИЕСЯ ЭЛМЕНТЫ!
     */
    public void insert(int value) { //пример вставки в корень
        if (root != null) { //если нода не пустая, то вставляем новую ноду со значением.
            insert(root, value);
            root = balance(root);
        } //вставка элемента напрямую.
        else { //если нода пустая,
            root = new Node(); //создаем новую ноду на месте ноды рут.
            root.value = value; //значением value становится значение кот-е пришло из параметра.
        }
        root.color = Color.BLACK; //вручную перекрашиваем рут на черный
    }

    private void insert(Node node, int value) { //если мы хотим вставить не в корень
        //надо проверить не совпали ли по значению с элем-м, кот-й мы хотим вставить.
        if (node.value != value) {//если значение ноды не совпадает со значением кот-й хотим вставить, то продолжаем алг-м.
            //так как вершина root занята надо выбрать влево или вправо помещать элем.:
            if (node.value < value) {//если значение кот-й мы хотим вставить больше значения ноды,то вставляем вправо.
                  if (node.right == null) {
                    node.right = new Node();
                    node.right.value = value;
                    node.right.color = Color.RED;
                } else {//нода не пустая,
                    insert(node.right, value);//см.в какую дочку можем положить элем. по правой ветке.
                    node.right = balance(node.right);
                }
            } else {//если пойдем по левой ветке
                if (node.left == null) {
                    node.left = new Node();
                    node.left.value = value;
                    node.right.color = Color.RED;
                } else {//нода не пустая,
                    insert(node.left, value);//выполняем рекурсивную вставку по левой ветке.
                    node.left = balance(node.left);

                }
            }
        }
    }

    //todo Реализуем метод поиска:
    //Добавляем метод find(int value) для проверки на существование элемента дерева со значе-м value.
    //Возвращаем объект Node, если нашли нужный элемент, иначе возвращаем null.
    public Node find(int value) {
        return find(root, value);
    }

    private Node find(Node node, int value) {
        if (node == null) //если не нашли параметр, то нода налл.
            return null;//то объекта value нет.
        if (node.value == value) { //если значение ноды совпадает со знач-м кот-й мы передаем/ищем.
            return node;//то возвращаем текущую ноду, кот-й является ответом.
        }
        if (node.value < value) { //если значение текущей ноды меньше значения параметра,кот-й передаем.
            return find(node.right, value);
        } else {
            return find(node.left, value);
        }
    }


    private Node leftRotate(Node node) { //поворот против час.стрелки и передаем ноду кот-ю поворачиваем.
        Node cur = node.right; // чтобы совершить лев.поворот надо сохранить ноду карент,кот-й находится по правой стороне от текущ.ноды
        node.right = cur.left; //переприсваиваем ссылки
        cur.left = node;
        cur.color = node.color;
        node.color = Color.RED;
        return cur;
    }
    private Node rightRotate(Node node) {
        Node cur = node.left; // чтобы совершить прав..поворот надо сохранить ноду карент,кот-й находится по левой стороне от текущ.ноды
         node.left = cur.right; //переприсваиваем ссылки
        cur.right = node;
        cur.color = node.color;
        node.color = Color.RED;
        return cur;
    }

    private void swapColors(Node node) {
        node.color = (node.color == Color.RED ? Color.BLACK : Color.RED); //если нода красного цвета, то меняем на черный,
        // иначе на красный.
        //цвета потомков меняем на черный:
        node.left.color = Color.BLACK;
        node.right.color = Color.BLACK;
    }
    private Node balance(Node node) {
        boolean flag =true;//хотим балансировать.
        Node res = node; //корень после балансировки
        do {
            flag = false; //как-бы цикл говорит: балансировать не надо!
//todo 1.Если правый дочерний элемент красный, а левый черный, то применяем малый правый поворот
            if(res.right != null && res.right.color == Color.RED && (res.left == null || res.left.color == Color.BLACK)){
                flag = true;
                res=leftRotate(res);


            }
//todo 2.Если левый дочерний элемент красный и его левый дочерний элемент тоже красный, то применяем малый левый поворот
            if(res.left != null && res.left.color == Color.RED && (res.left.left != null && res.left.left.color == Color.RED)){
                flag = true;
                res=rightRotate(res);



            }
//todo 3.- Если оба дочерних элемента красные, то делаем смену цвета
            if (res.left != null && res.left.color == Color.RED && res.right != null && res.right.color == Color.RED) {
                swapColors(res);
                flag = true;
            }
        }while(flag); //пост условие
        return  res;

    }
}


