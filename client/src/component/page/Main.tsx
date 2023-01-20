import ReservationSection from '../main/ReservationSection';
import FacilitiesSection from '../main/FacilitiesSection';
import WayToCome from '../main/WayToComeSection';

function Main() {
    return (
        <main className=''>
            <div className='bg'/>
            <section className='intro-section'>
                <div className="translate-x-16">
                    <h2 className='text-6xl'>Busan IT Hotel</h2>
                    <p className='text-xl'>It will be amazing experience with us.</p>
                </div>
            </section>
            <ReservationSection/>
            <FacilitiesSection/>
            <WayToCome/>
        </main>
    );
}
export default Main;
