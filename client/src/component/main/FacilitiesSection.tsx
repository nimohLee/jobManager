import React from "react";
import restaurant from "../../static/image/restaurant.jpg";
import gym from "../../static/image/gym.jpg";
function FacilitiesSection() {
    return (
        <section>
            <h2>Facilities</h2>
            <article>
                <img src={restaurant} alt="Restaurant Image" />
                <p>
                    Lorem ipsum dolor sit amet consectetur adipisicing elit.
                    Doloribus omnis error doloremque dicta ut! Necessitatibus
                    sunt provident aut. Natus beatae laboriosam ea doloribus
                    saepe nesciunt molestias non itaque sit enim.
                </p>
            </article>
            <article>
                <img src={gym} alt="Gym Image" />
                <p>
                    Lorem ipsum dolor sit amet consectetur adipisicing elit.
                    Doloribus omnis error doloremque dicta ut! Necessitatibus
                    sunt provident aut. Natus beatae laboriosam ea doloribus
                    saepe nesciunt molestias non itaque sit enim.
                </p>
            </article>
        </section>
    );
}

export default FacilitiesSection;
