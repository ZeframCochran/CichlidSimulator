/*
 * This name Generator will be use when the System needs to generate
 * a randomize object attributes.
 * Feel free to add or remove prefixes, roots or suffixes as needed.
 */
package cichlid_sim.gui;

import java.util.Random;

/**
 *
 * @author pros
 */
public class NameGen {
    private Random rand = new Random();
    private static String name;
    private String prefixes[]={"Ab","Ad","Anim","Ante","Anti","Auc","Aud","Ben","Cac","Circum","Co","Contra","Extra",
                                "Hetero","Homo","Hyper","Hypo","Il","Inter","Intra","Mal","Peri","Poly","Post","Pre","Syn"};
    private String roots[]={"ambu","fract", "frag", "frai","fight",
                            "andro","anthro","grad", "ress","sanct","bellu","belli","greg","scien","carn","gyn","senti",
                            "clam","claim","her","hes","somn","sop","clin","bend","jac","ject","son","clud","clus","claus","luc","lum",
                             "lus","spec","duc","omni","narco"};
                            /*= to throw	son = sound
                            clud, clus, claus = close	loq, log, loc, lix = talk	soph = wise
                            cred = trust, belief	luc, lum, lus = light, clear	spec = look
                            demo– = people	meta, mut = change	term = end, boundary
                            dog, dox = thought, idea	morph = shape	terr = earth
                            duc, duct = to lead, pull	narco = sleep	theo = God
                            ev = time, age	omni = all	ven = come
                            fac = to do, make	oper = work	vid, vis = to see
                            fiss = break, part	pac, plac = peace, calm	voc, voke = call
                            flict = strike	path = feeling	vol = roll, turn
                            fort = strength	phon = sound	xen = stranger};
                            * */
    private String suffixes[]={"able","ate","cess","cede","cide","cis","ette","lio","ist","logy","ology","ous"};

public NameGen()
{
 genName();
}

private void genName()
{
    
    if(rand.nextInt(3)<2)
    {
        String prefix = prefixes[rand.nextInt(prefixes.length)];
        String suffix = suffixes[rand.nextInt(suffixes.length)];
        name=prefix+suffix;
    }
    else
    {
        String prefix = prefixes[rand.nextInt(prefixes.length)];
        String suffix = suffixes[rand.nextInt(suffixes.length)];
        String root = roots[rand.nextInt(roots.length)];
        name=prefix+root+suffix;
    }
}

public static String getName()
{
    return name;
}
}