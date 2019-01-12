/**
 * Interficie comparator
 * @autor Gerard Melgares Martinez  i Josep Roig Torres
 * @version 1.0.0
 */
package Comparators;

import JSONClasses.Node;


public interface Comparator {
    public boolean compararp1top2 (Node node1, Node node2);
    public boolean compararp2top1 (Node node1, Node node2);
    public boolean compararp2top1IncludeEqual (Node node1, Node node2);
}
