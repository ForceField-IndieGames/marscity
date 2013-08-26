package objects;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import game.ResourceManager;

import java.io.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * This object loader loads an parses a .obj file
 * @author Benedikt Ringlein
 */

public class ObjectLoader {

    public static int createDisplayList(Mesh mesh) {
        int displayList = glGenLists(1);
        glNewList(displayList, GL_COMPILE);
        {
            glBegin(GL_TRIANGLES);
            for (Mesh.Face face : mesh.getFaces()) {
                if (face.hasTextureCoordinates()) {
                    glMaterial(GL_FRONT, GL_DIFFUSE, ResourceManager.toFlippedFloatBuffer(face.getMaterial()
                            .diffuseCol[0], face.getMaterial().diffuseCol[1],
                            face.getMaterial().diffuseCol[2], 1));
                    glMaterial(GL_FRONT, GL_AMBIENT, ResourceManager.toFlippedFloatBuffer(face.getMaterial()
                            .ambientCol[0], face.getMaterial().ambientCol[1],
                            face.getMaterial().ambientCol[2], 1));
                    glMaterialf(GL_FRONT, GL_SHININESS, face.getMaterial().specular);
                }
                if (face.hasNormals()) {
                    Vector3f n1 = mesh.getNormals().get(face.getNormalIndices()[0] - 1);
                    glNormal3f(n1.x, n1.y, n1.z);
                }
                if (face.hasTextureCoordinates()) {
                    Vector2f t1 = mesh.getTextureCoordinates().get(face.getTextureCoordinateIndices()[0] - 1);
                    glTexCoord2f(t1.x, -t1.y);
                }
                Vector3f v1 = mesh.getVertices().get(face.getVertexIndices()[0] - 1);
                glVertex3f(v1.x, v1.y, v1.z);
                if (face.hasNormals()) {
                    Vector3f n2 = mesh.getNormals().get(face.getNormalIndices()[1] - 1);
                    glNormal3f(n2.x, n2.y, n2.z);
                }
                if (face.hasTextureCoordinates()) {
                    Vector2f t2 = mesh.getTextureCoordinates().get(face.getTextureCoordinateIndices()[1] - 1);
                    glTexCoord2f(t2.x, -t2.y);
                }
                Vector3f v2 = mesh.getVertices().get(face.getVertexIndices()[1] - 1);
                glVertex3f(v2.x, v2.y, v2.z);
                if (face.hasNormals()) {
                    Vector3f n3 = mesh.getNormals().get(face.getNormalIndices()[2] - 1);
                    glNormal3f(n3.x, n3.y, n3.z);
                }
                if (face.hasTextureCoordinates()) {
                    Vector2f t3 = mesh.getTextureCoordinates().get(face.getTextureCoordinateIndices()[2] - 1);
                    glTexCoord2f(t3.x, -t3.y);//
                }
                Vector3f v3 = mesh.getVertices().get(face.getVertexIndices()[2] - 1);
                glVertex3f(v3.x, v3.y, v3.z);
            }
            glEnd();
        }
        glEndList();
        return displayList;
    }

    public static Mesh loadModel(String path, int LoD) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(ObjectLoader.class.getResourceAsStream(ResourceManager.objectspath+path+"/"+path+LoD+".obj")));
        Mesh model = new Mesh();
        Mesh.Material currentMaterial = new Mesh.Material();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("#")) {
                continue;
            }
            if (line.startsWith("mtllib ")) {
                BufferedReader materialFileReader = new BufferedReader(new InputStreamReader(ObjectLoader.class.getResourceAsStream(ResourceManager.objectspath+path+ "/" + path+".mtl")));
                String materialLine;
                Mesh.Material parseMaterial = new Mesh.Material();
                String parseMaterialName = "";
                while ((materialLine = materialFileReader.readLine()) != null) {
                    if (materialLine.startsWith("#")) {
                        continue;
                    }
                    if (materialLine.startsWith("newmtl ")) {
                        if (!parseMaterialName.equals("")) {
                            model.getMaterials().put(parseMaterialName, parseMaterial);
                        }
                        parseMaterialName = materialLine.split(" ")[1];
                        parseMaterial = new Mesh.Material();
                    } else if (materialLine.startsWith("Ns ")) {
                        parseMaterial.specular = Float.valueOf(materialLine.split(" ")[1]);
                    } else if (materialLine.startsWith("Ka ")) {
                        String[] rgb = materialLine.split(" ");
                        parseMaterial.ambientCol[0] = Float.valueOf(rgb[1]);
                        parseMaterial.ambientCol[1] = Float.valueOf(rgb[2]);
                        parseMaterial.ambientCol[2] = Float.valueOf(rgb[3]);
                    } else if (materialLine.startsWith("Ks ")) {
                        String[] rgb = materialLine.split(" ");
                        parseMaterial.specularCol[0] = Float.valueOf(rgb[1]);
                        parseMaterial.specularCol[1] = Float.valueOf(rgb[2]);
                        parseMaterial.specularCol[2] = Float.valueOf(rgb[3]);
                    } else if (materialLine.startsWith("Kd ")) {
                        String[] rgb = materialLine.split(" ");
                        parseMaterial.diffuseCol[0] = Float.valueOf(rgb[1]);
                        parseMaterial.diffuseCol[1] = Float.valueOf(rgb[2]);
                        parseMaterial.diffuseCol[2] = Float.valueOf(rgb[3]);
                    }
                }
                model.getMaterials().put(parseMaterialName, parseMaterial);
                materialFileReader.close();
            } else if (line.startsWith("usemtl ")) {
                currentMaterial = model.getMaterials().get(line.split(" ")[1]);
            } else if (line.startsWith("v ")) {
                String[] pos = line.split(" ");
                float x = Float.valueOf(pos[1]);
                float y = Float.valueOf(pos[2]);
                float z = Float.valueOf(pos[3]);
                model.getVertices().add(new Vector3f(x, y, z));
            } else if (line.startsWith("vn ")) {
                String[] pos = line.split(" ");
                float x = Float.valueOf(pos[1]);
                float y = Float.valueOf(pos[2]);
                float z = Float.valueOf(pos[3]);
                model.getNormals().add(new Vector3f(x, y, z));
            } else if (line.startsWith("vt ")) {
                String[] pos = line.split(" ");
                float s = Float.valueOf(pos[1]);
                float t = Float.valueOf(pos[2]);
                model.getTextureCoordinates().add(new Vector2f(s, t));
            } else if (line.startsWith("f ")) {
                String[] faceIndices = line.split(" ");
                int[] vertices = {Integer.parseInt(faceIndices[1].split("/")[0]),
                        Integer.parseInt(faceIndices[2].split("/")[0]), Integer.parseInt(faceIndices[3].split("/")[0])};
                int[] textureCoordinates = {-1, -1, -1};
                if (model.hasTextureCoordinates()) {
                    textureCoordinates[0] = Integer.parseInt(faceIndices[1].split("/")[1]);
                    textureCoordinates[1] = Integer.parseInt(faceIndices[2].split("/")[1]);
                    textureCoordinates[2] = Integer.parseInt(faceIndices[3].split("/")[1]);
                }
                int[] normals = {0, 0, 0};
                if (model.hasNormals()) {
                    normals[0] = Integer.parseInt(faceIndices[1].split("/")[2]);
                    normals[1] = Integer.parseInt(faceIndices[2].split("/")[2]);
                    normals[2] = Integer.parseInt(faceIndices[3].split("/")[2]);
                }

                model.getFaces().add(new Mesh.Face(vertices, normals,
                        textureCoordinates, currentMaterial));
            } else if (line.startsWith("s ")) {
                boolean enableSmoothShading = !line.contains("off");
                model.setSmoothShadingEnabled(enableSmoothShading);
            }
        }
        reader.close();
        return model;
    }
}