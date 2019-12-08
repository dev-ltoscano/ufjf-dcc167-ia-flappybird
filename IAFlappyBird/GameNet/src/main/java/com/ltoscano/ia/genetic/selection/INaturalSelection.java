package com.ltoscano.ia.genetic.selection;

import com.ltoscano.ia.genetic.Genome;
import java.util.List;

/**
 *
 * @author ltosc
 */
public interface INaturalSelection 
{
    public Genome selection(List<Genome> genomeList);
}
