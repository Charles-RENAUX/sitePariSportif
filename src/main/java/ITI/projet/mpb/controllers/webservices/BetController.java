package ITI.projet.mpb.controllers.webservices;

import ITI.projet.mpb.exceptions.BetAlreadyException;
import ITI.projet.mpb.exceptions.ClientNotFoundException;
import ITI.projet.mpb.pojos.Bet;
import ITI.projet.mpb.pojos.BetDto;
import ITI.projet.mpb.services.BetService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/bet")
public class BetController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BetDto> listAll(){
        return BetService.getInstance().listAll();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/sort")
    public List<BetDto> listBySort(
            @QueryParam("sort") String jsSort){
        return BetService.getInstance().listBySort(jsSort);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/filter")
    public List<BetDto> listByFilter(
            @QueryParam("filter") String jsFilter,
            @QueryParam("name") String name){
        return BetService.getInstance().listByFilter(jsFilter, name);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/pair")
    public List<BetDto> listbyPair(
            @QueryParam("sort") String jsSort,
            @QueryParam("filter") String jsFilter,
            @QueryParam("name") String name){
        return BetService.getInstance().listByPair(jsSort, jsFilter, name);

    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{idBet}")
    public BetDto getBet(@PathParam("idBet") Integer idBet){
        return BetService.getInstance().getBet(idBet);
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Response addBet(BetDto betDto){
        try{
            Bet betToAdd=new Bet(betDto);
            BetService.getInstance().addBet(betToAdd);
            return Response.status(Response.Status.CREATED).build();
        } catch (BetAlreadyException e) {
            return Response.status(Response.Status.CONFLICT).build();
        }catch (IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
}

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/edit")
    public Response editBet(BetDto betDto){
        try{
            Bet betToEdit=new Bet(betDto);
            BetService.getInstance().editBet(betToEdit);
            return Response.status(Response.Status.OK).build();
        }catch (ClientNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }catch (NullPointerException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @Path("/{idBet}")
    @DELETE
    public Response deleteBet(@PathParam("idBet") Integer idBet){
        try {
            BetService.getInstance().deleteBet(idBet);
            return Response.status(Response.Status.OK).build();
        } catch (ClientNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }
}
