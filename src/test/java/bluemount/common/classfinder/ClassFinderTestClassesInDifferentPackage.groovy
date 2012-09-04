package bluemount.common.classfinder


import bluemount.common.utils.MyBaseClass

public class ClassFinderTestClassesInDifferentPackage {


}

abstract class MyAnotherAbstractClass extends MyBaseClass {

}

class MySubClass3 extends MyAnotherAbstractClass {

}

class MySubClass31 extends MySubClass3 {

}

class MySubClass4 extends MyAnotherAbstractClass {

}
