package com.techelevator.controller;

import com.techelevator.dao.CatCardDao;
import com.techelevator.model.CatCard;
import com.techelevator.model.CatCardNotFoundException;
import com.techelevator.model.CatFact;
import com.techelevator.model.CatPic;
import com.techelevator.services.CatFactService;
import com.techelevator.services.CatPicService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")

public class CatController {

    private CatCardDao catCardDao;
    private CatFactService catFactService;
    private CatPicService catPicService;

    public CatController(CatCardDao catCardDao, CatFactService catFactService, CatPicService catPicService) {
        this.catCardDao = catCardDao;
        this.catFactService = catFactService;
        this.catPicService = catPicService;
    }

    /**
     * Returns a random CatCard
     *
     * @return random CatCard
     */
    @RequestMapping(path = "/cards/random", method = RequestMethod.GET)
    public CatCard getRandomCard()  {
        CatPic pic = catPicService.getPic();
        CatFact fact = catFactService.getFact();

        CatCard newCard = new CatCard();
        newCard.setImgUrl(pic.getFile());
        newCard.setCatFact(fact.getText());

        return newCard;
    }

    /**
     * Returns all CatCards
     *
     * @return all CatCards
     */
    @RequestMapping(path = "/cards", method = RequestMethod.GET)
    public List<CatCard> allCats() {return catCardDao.list();}

    /**
     *  Get CatCard by id
     *
     * @param id
     * @return a single CatCard
     */
    @RequestMapping(path = "/cards/{id}", method = RequestMethod.GET)
    public CatCard catById(@PathVariable long id) throws CatCardNotFoundException {return catCardDao.get(id);}

    /**
     *  Save a CatCard to User Collection
     *
     * @param card
     * @return save CatCard
     */

    @RequestMapping(path = "/cards", method = RequestMethod.POST)
    public boolean saveCat(@RequestBody CatCard card) {
        return catCardDao.save(card);
    }

    /**
     *
     * @param id
     * @param card
     * @return update CatCard
     */
    @RequestMapping(path = "/cards/{id}", method = RequestMethod.PUT)
    public boolean updateCat(@PathVariable long id, @RequestBody CatCard card) throws CatCardNotFoundException {
        return catCardDao.update(id, card);
    }

    /**
     *
     * @param id
     * @return delete CatCard
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/cards/{id}", method = RequestMethod.DELETE)
    public boolean deleteCat(@PathVariable long id) throws CatCardNotFoundException {
        return catCardDao.delete(id);
    }


}
