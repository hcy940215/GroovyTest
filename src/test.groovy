def str1 = '单引号'
def str2 = "双引号"
println "单引号计算" + str1.getClass().name
println "双引号计算" + str2.getClass().name

//单引号没有运算能力
def name = "张三"
println '单引号计算:${name}'
println "单引号计算:${name}"

//集合List
def list = [1, 23, 4, 5, 6, 7, 8]
println list.getClass().name  //输出java.util.ArrayList
println list[1]
println list[0]
println list[2]
println list[-1] //-1倒数左后最后一个
println list[2..4] //下标2-4的  中间两个.

//it 表示正在迭代的变量
list.each {
    print it
}

//集合Map
def map = ['width': 1024, 'height': 768]
println map.getClass().name  //输出java.util.LinkedHashMap
//；两种访问方式
println map.width
println map['height']

map.each {
    println "each方式：Key:${it.key} ,Value:${it.value}"

}
map.find {
    println "find方式：Key:${it.key} ,Value:${it.value}"
}
map.findAll {
    println "findAll方式：Key:${it.key} ,Value:${it.value}"
}

map.collect {
    println "collect方式：Key:${it.key} ,Value:${it.value}"
}

//两种当时方式调用
method1(1, 2)
method1 1, 2

//方法
def method1(int a, int b) {
    println a + b
}

def method2(int a, int b) {
    a > b ? a : b
}

//return可以不写
def add1 = method2(40, 6)
def add2 = method2(40, 60)
println "add1:${add1} add2:${add2}"

//代码块可以传参
//呆板的写法
list.each({ print it })
//格式化
list.each({
    print it
})

//Groovy规定 如果方法最后一个参数是闭包
//可以放在方法外面
list.each() {
    print it
}
//然后方法可以省略
list.each {
    print it
}

Person p = new Person()
println "名字：${p.name}"
p.name = "zhang"
println "名字：${p.name}"
//能访问不能修改 p.name=1 异常
println "年龄：${p.age}"

//闭包:闭包其实就是一个代码块
cusomEach {
    print it
}

//定义了一个方法cusomEach，它只有一个参数，用于接收一个闭包(代码块)
//
def cusomEach(it) {
    for (int i in 1..10) {
        it i
    }
}


eachMap { k, v ->
    println "Key:${k} ,Value:${v}"
}

def eachMap(closure) {
    def map = ["name": "huang", "age": 14]
    map.each {
        closure(it.key, it.value)
    }
}

class Delegate {
    def method1() {
        println "Delegate this:${this.getClass()} in Delegate"
        println "method1 in Delegate"
    }

    def task(closure) {
        closure this
    }
}

def method1() {
    println "Context this:${this.getClass()} in root"
    println "method1 in root"
}

//闭包委托
new Delegate().task {
    println "thisObject:${thisObject.getClass()}"
    println "owner:${owner.getClass()}"
    println "delegate:${delegate.getClass()}"

    method1()
    it.method1()
}


class Person2 {
    String personName
    int age

    def dumpPerson() {
        println "name is ${personName}, age:${age}"
    }
}

def person(Closure<Person2> closure) {
    Person2 p = new Person2()
    closure.delegate = p
    //委托优先
    closure.setResolveStrategy(Closure.DELEGATE_FIRST)
    closure p
}

person {
    personName="张三"
    age = 14
    dumpPerson()
}