package cichlid_sim.engine.scene.models;

/**
 * This class contains methods to load, store, and access all 3d model files
 * used in this program.
 *
 * @author Wesley Perry
 */
public class Models
{
    /**
     * This enum defines the different types of models this class is able to store.
     */
    public enum Type
    {
        FISH, POT, PLANT
    }
    
    /**
     * This method loads a model described by the model file located at modelPath.
     * 
     * @param modelPath the location of the model file.
     * @return the loaded model as a JME com.jme3.scene.Spatial.
     */
    public static com.jme3.scene.Spatial loadModelFromFile(String modelPath)
    {
        return cichlid_sim.engine.app.GameAppManager.getMainGame().getAssetManager().loadModel(modelPath);
    }
    /**
     * This method loads a model described by the model file located at modelPath.
     * 
     * @param modelPath the location of the model file.
     * @param texturePath the location of the model's texture file.
     * @return the loaded model as a JME com.jme3.scene.Spatial.
     */
    public static com.jme3.scene.Spatial loadModelFromFile(String modelPath, String texturePath)
    {
        return applyMaterialToModel(cichlid_sim.engine.app.GameAppManager.getMainGame().getAssetManager().loadModel(modelPath), texturePath);
    }
    
    /**
     * This method loads a model using the loadModelFromFile method and stores it
     * in the local ModelsCollection object using the addModel method.
     * 
     * @param name The name to assign to the loaded model.
     * @param type The type to assign to the loaded model.
     * @param modelPath The location of the model file.
     */
    public static void addModelFromFile(String name, Type type, String modelPath, com.jme3.math.ColorRGBA color)
    {
        addModel(name, type, applyMaterialToModel(loadModelFromFile(modelPath), color));
    }
    
    /**
     * This method loads a model using the loadModelFromFile method and stores it
     * in the local ModelsCollection object using the addModel method.
     * 
     * @param name The name to assign to the loaded model.
     * @param type The type to assign to the loaded model.
     * @param modelPath The location of the model file.
     * @param texturePath The location of the model's texture file.
     */
    public static void addModelFromFile(String name, Type type, String modelPath, String texturePath)
    {
        addModel(name, type, applyMaterialToModel(loadModelFromFile(modelPath), texturePath));
    }
    
    public static com.jme3.scene.Spatial applyMaterialToModel(com.jme3.scene.Spatial model, String texturePath)
    {
        model.setMaterial(applyTextureToMaterial(texturePath));
        return model;
    }
    
    public static com.jme3.scene.Spatial applyMaterialToModel(com.jme3.scene.Spatial model, com.jme3.math.ColorRGBA color)
    {
        com.jme3.material.Material material = createMaterial();
        material.setColor("Specular", com.jme3.math.ColorRGBA.White);
        material.setColor("Diffuse", color);
        material.setBoolean("UseMaterialColors", true);
        model.setMaterial(material);
        return model;
    }
    
    public static com.jme3.material.Material applyTextureToMaterial(String texturePath)
    {
        com.jme3.material.Material material = createMaterial();
        com.jme3.texture.Texture texture =  cichlid_sim.engine.app.GameAppManager.getMainGame().getAssetManager().loadTexture(texturePath);
        material.setTexture("DiffuseMap",texture);
        return material;
    }
    
    public static com.jme3.material.Material createMaterial() {
        com.jme3.material.Material material = new com.jme3.material.Material(cichlid_sim.engine.app.GameAppManager.getMainGame().getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        //Turns off backface culling (default) so the back side of models are rendered
        material.getAdditionalRenderState().setFaceCullMode(com.jme3.material.RenderState.FaceCullMode.Off);    
        return material;
    }
    
    /**
     * This method adds a model to the local ModelsCollection object.
     * 
     * @param name The name to assign to the loaded model.
     * @param type The type to assign to the loaded model.
     * @param model The JME com.jme3.scene.Spatial object which contains the model data.
     */
    public static void addModel(String name, Type type, com.jme3.scene.Spatial model)
    {
        ModelsCollection.addModel(name, type, model);
    }
    
    /**
     * Returns the stored model specified by the provided name.
     * @param name The name of the stored model to return.
     * @return the stored model specified by the provided name.
     */
    public static com.jme3.scene.Spatial getModel(String name)
    {
        return ModelsCollection.get(name);
    }
    
    /**
     * Returns All models of the requested type stored in the ModelsCollection object.
     * 
     * @return All models of the requested type stored in the ModelsCollection object.
     */
    public static java.util.ArrayList<com.jme3.scene.Spatial> getAllModelsOfType(Type type)
    {
        return ModelsCollection.getAllModelsOfType(type);
    }
    
    /**
     * Removes the model specified by the provided name from the ModelsCollection object.
     * 
     * @param name The name of the model to remove.
     * @return the removed model.
     */
    public static com.jme3.scene.Spatial removeModel(String name)
    {
        return ModelsCollection.remove(name);
    }
    
    /**
     * This is a basic collection class which contains methods to initialize and
     * maintain a collection of models.
     */
    private static class ModelsCollection
    {
        private static java.util.ArrayList<ModelsCollectionObject> modelsCollection = new java.util.ArrayList<>();
        
        /**
         * This method adds the provided model to the collection.
         * 
         * @param name The name to assign to the stored model.
         * @param type The type to assign to the stored model.
         * @param model The model to store.
         */
        public static void addModel(String name, Type type, com.jme3.scene.Spatial model)
        {
            modelsCollection.add(new ModelsCollectionObject(name, type, model));
        }
        
        /**
        * Returns the stored model specified by the provided name.
        * @param name The name of the stored model to return.
        * @return the stored model specified by the provided name.
         */
        public static com.jme3.scene.Spatial get(String name)
        {
            for(ModelsCollectionObject mCO : modelsCollection)
            {
                if(mCO.getName().equalsIgnoreCase(name))
                {
                    return mCO.getModel();
                }
            }
            return null;
        }
        
        /**
         * Returns all models of the requested type that are stored in the collection.
         * 
         * @return an ArrayList containing all stored models of the requested type.
         */
        public static java.util.ArrayList<com.jme3.scene.Spatial> getAllModelsOfType(Type type)
        {
            java.util.ArrayList<com.jme3.scene.Spatial> allRequestedModels = new java.util.ArrayList<>();
            
            for(ModelsCollectionObject mCO : modelsCollection)
            {
                if(mCO.getType() == type)
                {
                    allRequestedModels.add(mCO.getModel());
                }
            }
            return allRequestedModels;
        }
        
        /**
         * Removes the specified model from the collection.
         * 
         * @param name The name of the model to remove.
         * @return the removed model.
         */
        public static com.jme3.scene.Spatial remove(String name)
        {
            for(ModelsCollectionObject mCO : modelsCollection)
            {
                if(mCO.getName().equalsIgnoreCase(name))
                {
                    if(modelsCollection.remove(mCO))
                    {
                        return mCO.getModel();
                    }
                }
            }
            return null;
        }
        
        /**
         * This class defines a custom structure to enable storing model files
         * with their names and types.
         */
        private static class ModelsCollectionObject
        {
            private String name;
            private Type type;
            private com.jme3.scene.Spatial model;
            
            /**
             * Creates a new ModelsCollectionObject and initializes it with the 
             * provided values.
             * 
             * @param n The name of the model.
             * @param t The type of the model.
             * @param m The model.
             */
            public ModelsCollectionObject(String n, Type t, com.jme3.scene.Spatial m)
            {
                name = n;
                type = t;
                model = m;
            }
            
            /**
             * Returns the name of the model.
             * 
             * @return the name of the model.
             */
            public String getName()
            {
                return name;
            }
            
            /**
             * Returns the type of the model.
             * @return the type of the model.
             */
            public Type getType()
            {
                return type;
            }
            
            /**
             * Returns the model.
             * @return the model.
             */
            public com.jme3.scene.Spatial getModel()
            {
                return model.clone();
            }
        }
    }
}
