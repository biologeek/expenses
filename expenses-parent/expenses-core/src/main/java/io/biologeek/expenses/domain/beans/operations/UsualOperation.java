package io.biologeek.expenses.domain.beans.operations;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("U")
public class UsualOperation extends Operation {

}
