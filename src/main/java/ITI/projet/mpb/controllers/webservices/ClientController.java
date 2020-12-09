package ITI.projet.mpb.controllers.webservices;

import ITI.projet.mpb.exceptions.ClientAlreadyException;
import ITI.projet.mpb.exceptions.ClientNotFoundException;
import ITI.projet.mpb.pojos.Client;
import ITI.projet.mpb.pojos.ClientDto;
import ITI.projet.mpb.services.ClientService;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Path("/clients")
public class ClientController {
    private static final Logger LOGGER= LogManager.getLogger();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> listAll() {
        List <Client> list = ClientService.getInstance().listAll();
        LOGGER.debug("GET List<Client> /client/all 200 OK");
        return list;
    }

    @Path("/sort")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> list(
            @QueryParam("sort") String jsSort) {
        List <Client> list =ClientService.getInstance().list(jsSort);
        LOGGER.debug("GET List<Client> /client/sort?sort={} 200 OK",jsSort);
        return list;
    }


    @Path("/byrole")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> listByRole(
            @QueryParam("role") Integer role,
            @QueryParam("sort") String Jssort) {
        List<Client> list = ClientService.getInstance().listByRole(role, Jssort);
        LOGGER.debug("GET List<Client> /client/byrole?role={}&sort={} 200 OK",role,Jssort);
        return list;
    }

    @Path("/pseudo/{pseudo}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Client getClientViaPseudo(@PathParam("pseudo") String pseudo) {
        Client client = ClientService.getInstance().getClientViaPseudo(pseudo);
        LOGGER.debug("GET Client /client/pseudo/{} 200 OK",pseudo);
        return client;
    }

    @Path("/email/{email}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Client getClientViaEmail(@PathParam("email") String email) {
        Client client= ClientService.getInstance().getClientViaEmail(email);
        LOGGER.debug("GET Client /client/email/{} 200 OK",email);
        return client;
    }

    @Path("/{idClient}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Client getClient(@PathParam("idClient") Integer idClient) {
        Client client= ClientService.getInstance().getClient(idClient);
        LOGGER.debug("GET Client /client/{} 200 OK",idClient);
        return client;
    }

    @Path("/{idClient}")
    @DELETE
    public Response deleteClient(@PathParam("idClient") Integer idClient) {
        try {
            ClientService.getInstance().deleteClient(idClient);
            LOGGER.debug("DELETE /client/{} 200 OK",idClient);
            return Response.status(Response.Status.OK).build();
        } catch (ClientNotFoundException e) {
            LOGGER.error("DELETE /client/{} 404 NOT FOUND",idClient);
            return Response.status(Response.Status.NOT_FOUND).build();
        }catch (IllegalArgumentException e){
            LOGGER.error("DELETE /client/{} 400 BAD REQUEST",idClient);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }catch (Exception e){
            LOGGER.error("DELETE /client/{} 304 NOT MODIFIED",idClient);
            e.printStackTrace();
            return Response.status(Response.Status.NOT_MODIFIED).build();
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
            LOGGER.debug("POST /client/add   pseudo={} 201 CREATED",clientDto.getPseudo());
            return Response.status(Response.Status.CREATED).build();
        } catch (ClientAlreadyException e) {
            LOGGER.error("POST /client/add   pseudo={} 409 CONFLICT",clientDto.getPseudo());
            return Response.status(Response.Status.CONFLICT).build();
        }catch (IllegalArgumentException e){
            LOGGER.error("POST /client/add   pseudo={} 400 BAD REQUEST ",clientDto.getPseudo());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("POST /client/add   pseudo={} 304 NOT MODIFIED ",clientDto.getPseudo());
            return Response.status(Response.Status.NOT_MODIFIED).build();
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
            LOGGER.error("PATCH /client   pseudo={} 404 NOT FOUND ",clientDto.getPseudo());
            return Response.status(Response.Status.NOT_FOUND).build();
        }catch (ClientAlreadyException e){
            LOGGER.error("PATCH /client   pseudo={} 409 CONFLICT ",clientDto.getPseudo());
            return Response.status(Response.Status.CONFLICT).build();
        }
        catch (IllegalArgumentException e){
            LOGGER.error("PATCH /client   pseudo={} 400 BAD REQUEST ",clientDto.getPseudo());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("PATCH /client   pseudo={} 304 NOT MODIFIED ",clientDto.getPseudo());
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }
}