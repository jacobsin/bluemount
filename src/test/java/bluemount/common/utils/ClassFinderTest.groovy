package bluemount.common.utils

import bluemount.common.classfinder.ClassFinderTestClassesInDifferentPackage
import bluemount.common.classfinder.MySubClass3
import bluemount.common.classfinder.MySubClass31
import bluemount.common.classfinder.MySubClass4
import org.junit.Test

public class ClassFinderTest {

  @Test
  public void findAllNonAbstractStaticSubclassesOfBaseClassUnderTheSamePackage() {
    assert new ClassFinder().findSubclasses(MyBaseClass) == [MyBaseClass, MySubClass1, MySubClass11, MySubClass2].toSet()
    assert new ClassFinder().findSubclasses(MySubClass1) == [MySubClass1, MySubClass11].toSet()
    assert new ClassFinder().findSubclasses(MySubClass2) == [MySubClass2].toSet()
  }

  @Test
  public void findAllNonAbstractStaticSubclassesOfBaseClassUnderADifferentPackage() {
    assert new ClassFinder().findSubclasses(ClassFinderTestClassesInDifferentPackage.package.name, MyBaseClass) == [MySubClass3, MySubClass31, MySubClass4].toSet()
  }

  @Test
  public void findAllNonAbstractStaticSubclassesOfBaseClassIncludingThoseInSubpackages() {
    String subpackage = ClassFinderTestClassesInDifferentPackage.package.name
    String parentPackage = subpackage.substring(0, subpackage.lastIndexOf('.'));

    assert new ClassFinder().findSubclasses(parentPackage, MyBaseClass) == [MySubClass3, MySubClass31, MySubClass4, MyBaseClass, MySubClass1, MySubClass11, MySubClass2].toSet()
  }

  @Test
  public void findAllNonAbstractStaticSubclassesOfInterfaceUnderTheSamePackage() {
    assert new ClassFinder().findSubclasses(MyInterface) == [MyBaseClass, MySubClass1, MySubClass11, MySubClass2].toSet()
  }

  @Test
  public void findAllNonAbstractStaticSubclassesOfInterfaceUnderADifferentPackage() {
    assert new ClassFinder().findSubclasses(ClassFinderTestClassesInDifferentPackage.getPackage().getName(), MyInterface) == [MySubClass3, MySubClass31, MySubClass4].toSet()
  }

  @Test
  public void testFindAllNonAbstractStaticSubclassesOfInterfaceIncludingThoseInSubpackages() {
    String subpackage = ClassFinderTestClassesInDifferentPackage.package.name
    String parentPackage = subpackage.substring(0, subpackage.lastIndexOf('.'));

    assert new ClassFinder().findSubclasses(parentPackage, MyInterface) == [MySubClass3, MySubClass31, MySubClass4, MyBaseClass, MySubClass1, MySubClass11, MySubClass2].toSet()
  }

  @Test
  public void findAllNonAbstractStaticClassesWithNameRegex() {
    assert new ClassFinder().findClasses(MyBaseClass.package.name, ".*MySubClass[12]") == [MySubClass1, MySubClass2].toSet()
  }
}

interface MyInterface {

}

class MyBaseClass implements MyInterface {

}

abstract class MyAbstractClass extends MyBaseClass {

}

class MySubClass1 extends MyAbstractClass {

}

class MySubClass2 extends MyAbstractClass {

}

class MySubClass11 extends MySubClass1 {

}