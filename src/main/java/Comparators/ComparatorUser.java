package Comparators;

import JSONClasses.Node;
import JSONClasses.User;

public interface ComparatorUser {
    public boolean compararp1top2 (User user1, User user2);
    public boolean compararp2top1 (User user1, User user2);
    public boolean compararp2top1IncludeEqual (User user1, User user2);
}
