package Comparators;


import JSONClasses.Server;

import java.util.List;

public interface ComparatorServerList {
    public boolean compararp1top2 (List<Server> llista1, List<Server> llista2, int numServers);
    public boolean compararp2top1 (List <Server> llista1, List <Server> llista2, int numServers);
    public boolean compararp2top1IncludeEqual (List <Server> llista1, List <Server> llista2, int numServers);
}
