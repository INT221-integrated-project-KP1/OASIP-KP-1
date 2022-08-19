<script setup>

</script>

<template>

    <section class="position-relative py-4 py-xl-5">
        <div class="container position-relative">
            <div class="row d-flex justify-content-center">
                <div class="col-md-8 col-lg-6 col-xl-5 col-xxl-4">
                    <div class="card mb-5">
                        <div class="card-body p-sm-5">
                            <h2 class="text-center mb-4">Sign Up</h2>
                            <form method="post">
                                <div class="mb-3"><input class="form-control" type="text" id="name" name="name"
                                        placeholder="Name"></div>
                                <div class="mb-3"><input class="form-control" type="email" id="email" name="email"
                                        placeholder="Email"></div>
                                <div class="mb-3"><input class="form-control" type="role" id="role" name="role"
                                        placeholder="role">
                                    <div class="mb-3"></div>
                                </div>
                                <div><button class="btn btn-primary d-block w-100" type="submit">Send </button></div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</template>

<style scoped>
</style>