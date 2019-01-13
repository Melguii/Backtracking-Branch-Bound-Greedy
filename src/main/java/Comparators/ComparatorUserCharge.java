package Comparators;

import JSONClasses.Server;
import JSONClasses.User;
import utils.AlgorismesExtres;

import java.util.List;

public class ComparatorUserCharge implements ComparatorUser{
    public boolean compararp1top2(User user1, User user2) {
        if (user1.getActivity() > user2.getActivity()) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean compararp2top1(User user1, User user2) {
        if (user1.getActivity() < user2.getActivity()) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean compararp2top1IncludeEqual(User user1, User user2) {
        if (user1.getActivity() <= user2.getActivity()) {
            return true;
        }
        else {
            return false;
        }
    }
}
