package ITI.projet.mpb.controllers.webservices;

import ITI.projet.mpb.exceptions.ClientAlreadyException;
import ITI.projet.mpb.exceptions.ClientNotFoundException;
import ITI.projet.mpb.pojos.Client;
import ITI.projet.mpb.pojos.ClientDto;
import ITI.projet.mpb.pojos.ClientMdp;
import ITI.projet.mpb.services.ClientService;
import ITI.projet.mpb.services.MotDePasseServiceHash;

import javax.management.RuntimeErrorException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/clients")
public class ClientController {

    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> listAll() {
        System.out.println("Hello");
        return ClientService.getInstance().listAll();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> list(
            @QueryParam("sort") String jsSort) {
        return ClientService.getInstance().list(jsSort);
    }


    @Path("/byrole")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> listByRole(
            @QueryParam("role") Integer role,
            @QueryParam("sort") String Jssort) {
        return ClientService.getInstance().listByRole(role, Jssort);
    }

    @Path("/pseudo/{pseudo}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Client getClientViaPseudo(@PathParam("pseudo") String pseudo) {
        return ClientService.getInstance().getClientViaPseudo(pseudo);
    }

    @Path("/email/{email}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Client getClientViaEmail(@PathParam("email") String email) {
        return ClientService.getInstance().getClientViaEmail(email);
    }

    @Path("/{idClient}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Client getClient(@PathParam("idClient") Integer idClient) {
        return ClientService.getInstance().getClient(idClient);
    }

    @Path("/{idClient}")
    @DELETE
    public Response deleteClient(@PathParam("idClient") Integer idClient) {
        try {
            ClientService.getInstance().deleteClient(idClient);
            return Response.status(Response.Status.OK).build();
        } catch (ClientNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCLient(ClientDto clientDto) {
        try {
            String pwd = ClientService.getInstance().getDefaultPwd();
            Client clientToAdd = new Client(clientDto, pwd);
            ClientService.getInstance().addClient(clientToAdd);
            return Response.status(Response.Status.CREATED).build();
        } catch (ClientAlreadyException e) {
            return Response.status(Response.Status.CONFLICT).build();
        } catch (RuntimeErrorException e) {
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editClient(ClientDto clientDto){
        try{
            Integer edit=clientDto.getModif();
            if (edit==0){
                String pwd=ClientService.getInstance().getDefaultPwd();
                Client clientToAdd=new Client(clientDto,pwd);
                ClientService.getInstance().editClient(clientToAdd);

                return Response.status(Response.Status.OK).build();
            }else if (edit==1){
                Client clientToAdd=new Client(clientDto,"");

                ClientService.getInstance().editClientNoPwd(clientToAdd);
                return Response.status(Response.Status.OK).build();
            }else{
                return Response.status(Response.Status.UNSUPPORTED_MEDIA_TYPE).build();
            }
            //Appelez le service pour éditer le client

        }catch (ClientNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @Path("/editpwd")
    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editPwd(ClientMdp clientMdp){
        if (!clientMdp.getPwd1().equals(clientMdp.getPwd2())){
           return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        Client clientcheck=ClientService.getInstance().getClient(clientMdp.getId());
        if (clientcheck==null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if(!MotDePasseServiceHash.validerMotDePasse(clientMdp.getOldPwd(),clientcheck.getMotDePasse())){
            return Response.status(Response.Status.CONFLICT).build();
        }
        else {
            clientcheck.setMotDePasse(MotDePasseServiceHash.genererMotDePasse(clientMdp.getPwd1()));
            ClientService.getInstance().editClient(clientcheck);
            return Response.status(Response.Status.ACCEPTED).build();
        }
    }

}