import java.util.List;
import java.util.Date;

public class Course {

    private final Long uuid;

    private final String name;

    private final List<Session> sessions;

    public Course(final Long uuid, final String name, final List<Session> sessions) {
        this.uuid = uuid;
        this.name = name;
        this.sessions = sessions;
    }

    private Long getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    @Override
    public boolean equals(final Object object) {
        // BEGIN (write your solution here)
        if (!(object instanceof Course)) {
            return false;
        }
        if (this.getUuid().longValue() == ((Course) object).getUuid().longValue() ) {
            return true;
        } else {
            return  false;
        }

        // END
    }

    public class Session {

        private final Date startDate;

        Session(final Date startDate) {
            this.startDate = startDate;
        }

        Date getStartDate() {
            return this.startDate;
        }

        Course getCourse() {
            return Course.this;
        }

        @Override
        public boolean equals(final Object object) {
            // BEGIN (write your solution here)
            if (!(object instanceof Session)) {
                return false;
            }
            if (this.getStartDate().equals(((Session) object).getStartDate()) & this.getCourse().uuid.longValue() == ((Session) object).getCourse().uuid.longValue()) {
                return true;
            } else {
                return false;
            }


            // END
        }
    }
}
