package org.enricogiurin.ocp17.book.ch7.useofenum;

enum Color {
  RED, GREEN
}

//Cannot inherit from enum 'org.enricogiurin.ocp17.ch7.useofenum.Color'
enum AllColor /**extends Color**/
{

}

public class EnumExtended {
//enum cannot be extended
}
