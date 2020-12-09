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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Path("/bets")
public class BetController {
    private static final Logger LOGGER = LogManager.getLogger();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BetDto> listAll() {
        List<BetDto> list = BetService.getInstance().listAll();
        LOGGER.debug("GET List<Client> /bets 200 OK");
        return list;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/sort")
    public List<BetDto> listBySort(
            @QueryParam("sort") String jsSort) {
        List<BetDto> list = BetService.getInstance().listBySort(jsSort);
        LOGGER.debug("GET List<Client> /bets/sort?sort={} 200 OK",jsSort);
        return list;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/filter")
    public List<BetDto> listByFilter(
            @QueryParam("filter") String jsFilter,
            @QueryParam("name") String name) {
        List<BetDto> list = BetService.getInstance().listByFilter(jsFilter, name);
        LOGGER.debug("GET List<Client> /bets/filter?filter={}&name={} 200 OK",jsFilter,name);
        return list;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/pair")
    public List<BetDto> listbyPair(
            @QueryParam("sort") String jsSort,
            @QueryParam("filter") String jsFilter,
            @QueryParam("name") String name){
        List<BetDto> list=  BetService.getInstance().listByPair(jsSort, jsFilter, name);
        LOGGER.debug("GET List<Client> /bets/pair?filter={}&name={}&sort={} 200 OK",jsFilter,name,jsSort);
        return list;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{idBet}")
    public BetDto getBet(@PathParam("idBet") Integer idBet){
        BetDto bet= BetService.getInstance().getBet(idBet);
        LOGGER.debug("GET Client /bets/{} 200 OK",idBet);
        return bet;
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Response addBet(BetDto betDto){
        try{
            Bet betToAdd=new Bet(betDto);
            BetService.getInstance().addBet(betToAdd);
            LOGGER.debug("POST Client /client/add  dateMatch={} 201 CREATED",betDto.getDateMatch());
            return Response.status(Response.Status.CREATED).build();
        } catch (BetAlreadyException e) {
            LOGGER.error("POST Client /client/add  dateMatch={} 409 CONFLICT",betDto.getDateMatch());
            return Response.status(Response.Status.CONFLICT).build();
        }catch (IllegalArgumentException e){
            LOGGER.error("POST Client /client/add  dateMatch={} 400 BAD REQUEST",betDto.getDateMatch());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("POST Client /client/add  dateMatch={} 304 NOT MODIFIED",betDto.getDateMatch());
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
            LOGGER.debug("PATCH Client /client/edit dateMatch={} 200 OK",betDto.getDateMatch());
            return Response.status(Response.Status.OK).build();
        }catch (ClientNotFoundException e) {
            LOGGER.error("PATCH Client /client/edit  dateMatch={} 404 NOT FOUND",betDto.getDateMatch());
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            LOGGER.error("PATCH Client /client/edit  dateMatch={} 400 BAD REQUEST",betDto.getDateMatch());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }catch (NullPointerException e){
            LOGGER.error("PATCH Client /client/edit  dateMatch={} 400 BAD REQUEST",betDto.getDateMatch());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("PATCH Client /client/edit  dateMatch={} 304 NOT MODIFIED",betDto.getDateMatch());
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @Path("/{idBet}")
    @DELETE
    public Response deleteBet(@PathParam("idBet") Integer idBet){
        try {
            BetService.getInstance().deleteBet(idBet);
            LOGGER.debug("DELETE Client /client/{} 200 OK",idBet);
            return Response.status(Response.Status.OK).build();
        } catch (ClientNotFoundException e) {
            LOGGER.error("DELETE Client /client/{} 404 NOT FOUND",idBet);
            return Response.status(Response.Status.NOT_FOUND).build();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            LOGGER.error("DELETE Client /client/{} 400 BAD REQUEST",idBet);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("DELETE Client /client/{} 304 NOT NODIFIED",idBet);
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }
}
