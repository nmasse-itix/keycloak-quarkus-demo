package fr.itix.petstore;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;

import javax.annotation.security.RolesAllowed;

@Path("/pets")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PetstoreResource {
    static Set<Pet> pets = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));
    static int nextID;

    static {
        pets.add(new Pet(1, "Eclair", "cat"));
        pets.add(new Pet(2, "Cannelle", "cat"));
        nextID = 3;
    }

    @POST
    //@RolesAllowed({"admin"})
    public Pet add(Pet pet) {
        pet.id = nextID;
        nextID++;
        pets.add(pet);
        return pet;
    }

    @GET
    //@RolesAllowed({"user"})
    public Set<Pet> list() {
        return pets;
    }

    @GET
    @Path("/{id}")
    //@RolesAllowed({"user"})
    public Pet get(@PathParam(value = "id") int id) {
        return pets.stream().filter(p -> p.id == id).findFirst().get();
    }

    @PUT
    @Path("/{id}")
    //@RolesAllowed({"admin"})
    public Pet update(@PathParam(value = "id") int id, Pet pet) {
        Pet oldPet = pets.stream().filter(p -> p.id == id).findFirst().get();
        oldPet.name = pet.name;
        oldPet.tag = pet.tag;
        return pet;
    }
    @DELETE
    @Path("/{id}")
    //@RolesAllowed({"admin"})
    public Pet remove(@PathParam(value = "id") int id) {
        Pet oldPet = pets.stream().filter(p -> p.id == id).findFirst().get();
        pets.remove(oldPet);
        return oldPet;
    }

}
