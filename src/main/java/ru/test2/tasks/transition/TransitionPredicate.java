package ru.test2.tasks.transition;

import org.jetbrains.annotations.NotNull;
import ru.test.mock.bz.BzMszTransition;
import ru.test.mock.hbase.MszStage;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.function.Predicate;

class TransitionPredicate {
  private TransitionPredicate() {
  }

  @NotNull
  static Predicate<BzMszTransition> getPredicate(@Nonnull final Optional<MszStage> mszStageOptional) {
    final Predicate<BzMszTransition> bzMszTransitionPredicate;
    if (mszStageOptional.isEmpty()
        || mszStageOptional.get().getBzMszTransactionStagesId() == null) {
      bzMszTransitionPredicate = bzMszTransition -> bzMszTransition.getFromBzMszStageId() == null;
    } else {
      bzMszTransitionPredicate = bzMszTransition -> {
        final String bzMszStageId = mszStageOptional.get().getBzMszStageId();
        return bzMszTransition.getFromBzMszStageId().equals(bzMszStageId);
      };
    }
    return bzMszTransitionPredicate;
  }
}
