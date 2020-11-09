/*
 * package edu.poly.spring.controllers;
 * 
 * import java.util.ArrayList; import java.util.List; import java.util.Optional;
 * 
 * import javax.persistence.EntityManagerFactory; import javax.validation.Valid;
 * 
 * import org.hibernate.Query; import org.hibernate.Session; import
 * org.springframework.beans.BeanUtils; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.ModelMap; import
 * org.springframework.validation.BindingResult; import
 * org.springframework.validation.annotation.Validated; import
 * org.springframework.web.bind.annotation.PathVariable; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestBody; import
 * org.springframework.web.bind.annotation.RequestMapping;
 * 
 * import edu.poly.spring.dtos.RateDTO; import edu.poly.spring.dtos.ShowRate;
 * import edu.poly.spring.models.Rate; import edu.poly.spring.models.Shop;
 * import edu.poly.spring.models.User; import
 * edu.poly.spring.services.RateService; import
 * edu.poly.spring.services.ShopService; import
 * edu.poly.spring.services.UserService;
 * 
 * @Controller public class RateController {
 * 
 * @Autowired private RateService rateService;
 * 
 * @Autowired private EntityManagerFactory entityManagerFactory;
 * 
 * @RequestMapping("/showrate/{id}") public String showRate(ModelMap
 * model, @PathVariable(name = "id") int id) {
 * 
 * List<ShowRate> listR = new ArrayList<ShowRate>();
 * 
 * Session session =
 * entityManagerFactory.createEntityManager().unwrap(Session.class); Query query
 * = session.createSQLQuery("DECLARE @RC int\r\n" + "DECLARE @ID int\r\n" +
 * "EXECUTE @RC = [dbo].[sp_AVGRate] \r\n" + "   @ID = " + id);
 * 
 * List<Object[]> list = query.list();
 * 
 * System.out.println(list.size()); if (list.size() == 0 ) { ShowRate sr = new
 * ShowRate(id, 0.0); listR.add(sr); model.addAttribute("rateDTO", new Rate());
 * model.addAttribute("showRate", listR); model.addAttribute("listRated",
 * rateService.findAllByShop_idLike(id));
 * 
 * } else { for (Object[] object : list) { String shop_id =
 * String.valueOf(object[0]); String point = String.valueOf(object[1]);
 * 
 * ShowRate sr = new ShowRate(Integer.valueOf(shop_id), Double.valueOf(point));
 * listR.add(sr); }
 * 
 * model.addAttribute("rateDTO", new Rate()); model.addAttribute("showRate",
 * listR); model.addAttribute("listRated",
 * rateService.findAllByShop_idLike(id)); model.addAttribute("userRated",
 * rateService.findByShop_idAndUser_id(id, 2)); }
 * 
 * return ("posting/postingDetail"); }
 * 
 * @RequestMapping("/rating") public String add(ModelMap model) {
 * 
 * RateDTO dto = new RateDTO();
 * 
 * model.addAttribute("rateDTO", dto);
 * 
 * return "posting/postingDetail"; }
 * 
 * @PostMapping("/saveRate") public String saveRate(ModelMap model, @Validated
 * RateDTO rateDTO, BindingResult result) {
 * 
 * Rate rate = new Rate(); rate.setId(rateDTO.getId());
 * rate.setPoint(rateDTO.getPoint()); rate.setReason(rateDTO.getReason());
 * 
 * Shop shop = new Shop(); shop.setId(2);
 * 
 * User user = new User(); user.setId(1);
 * 
 * rate.setShop(shop); rate.setUser(user);
 * 
 * rateService.save(rate);
 * 
 * rateDTO.setId(rate.getId());
 * 
 * model.addAttribute("rateDTO", rateDTO);
 * 
 * return showRate(model, 1); }
 * 
 * }
 */