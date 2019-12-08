package com.ltoscano.ia.util.sort;

import com.ltoscano.ia.genetic.Genome;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ltosc
 */
public class SortHelper
{
    public static Map<Integer, Genome> sortMap(Map<Integer, Genome> map)
    {
        List<Map.Entry<Integer, Genome>> list = new LinkedList<>(map.entrySet());
        
        Collections.sort( list, new Comparator<Map.Entry<Integer, Genome>>()
        {
            @Override
            public int compare(Map.Entry<Integer, Genome> o1, Map.Entry<Integer, Genome> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        } );

        Map<Integer, Genome> result = new LinkedHashMap<>();
        
        for (Map.Entry<Integer, Genome> entry : list)
        {
            result.put(entry.getKey(), entry.getValue());
        }
        
        return result;
    }
}
