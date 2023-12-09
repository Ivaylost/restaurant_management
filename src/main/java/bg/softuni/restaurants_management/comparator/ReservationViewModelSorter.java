package bg.softuni.restaurants_management.comparator;

import bg.softuni.restaurants_management.error.ObjectNotFoundException;
import bg.softuni.restaurants_management.model.dto.ReservationViewModel;

import java.util.Comparator;

public class ReservationViewModelSorter implements Comparator<ReservationViewModel>{
        @Override
        public int compare(ReservationViewModel r1, ReservationViewModel r2) {
            if(r1.getDate() == null || r2.getDate() == null) {
                throw new ObjectNotFoundException("Not found reservation!");
            }

            int i = r1.getDate().compareToIgnoreCase(r2.getDate());

            return r1.getDate().compareToIgnoreCase(r2.getDate());
        }
    }
