const app = document.getElementById("app");
const pageSize = 5;

window.addEventListener("hashchange", renderRoute);
window.addEventListener("load", () => {
    if (!location.hash) {
        location.hash = "#/posts";
        return;
    }
    renderRoute();
});

async function renderRoute() {
    const hash = location.hash.replace(/^#/, "") || "/posts";
    const [path, queryString = ""] = hash.split("?");
    const query = new URLSearchParams(queryString);

    if (path === "/posts") {
        await renderPostList(Number(query.get("page") || 1));
        return;
    }

    if (path === "/new") {
        renderCreateForm();
        return;
    }

    const detailMatch = path.match(/^\/posts\/(\d+)$/);
    if (detailMatch) {
        await renderPostDetail(detailMatch[1]);
        return;
    }

    const editMatch = path.match(/^\/posts\/(\d+)\/edit$/);
    if (editMatch) {
        await renderEditForm(editMatch[1]);
        return;
    }

    app.innerHTML = `<section class="panel"><h2>Not Found</h2></section>`;
}

async function renderPostList(page) {
    setLoading();
    try {
        const data = await request(`/api/posts?page=${page}&size=${pageSize}`);
        const items = data.posts.map((post) => `
            <article class="item">
                <h3><a href="#/posts/${post.id}">${escapeHtml(post.title)}</a></h3>
                <p>${formatDate(post.createdAt)} · 조회수 ${post.views}</p>
            </article>
        `).join("");

        const pagination = buildPagination(page, data.totalPages || 1);

        app.innerHTML = `
            <section class="panel">
                <div class="row">
                    <h2>게시글 목록</h2>
                    <button class="btn btn-primary" data-route="#/new">새 글</button>
                </div>
                <div class="list">
                    ${items || `<p class="muted">등록된 게시글이 없습니다.</p>`}
                </div>
                ${pagination}
            </section>
        `;
        bindRouteButtons();
    } catch (error) {
        renderError(error);
    }
}

async function renderPostDetail(id) {
    setLoading();
    try {
        const post = await request(`/api/posts/${id}`);
        const tags = (post.tags || []).map((tag) => `<span>#${escapeHtml(tag)}</span>`).join(" ");
        app.innerHTML = `
            <section class="panel">
                <div class="row">
                    <h2>${escapeHtml(post.title)}</h2>
                    <span class="muted">조회수 ${post.views}</span>
                </div>
                <p class="muted">${formatDate(post.createdAt)}${post.updatedAt ? ` (수정 ${formatDate(post.updatedAt)})` : ""}</p>
                <p>${escapeHtml(post.content)}</p>
                <p class="muted">${tags}</p>
                <div class="row">
                    <button class="btn btn-line" data-route="#/posts">목록</button>
                    <div>
                        <button class="btn btn-line" data-route="#/posts/${post.id}/edit">수정</button>
                        <button class="btn btn-danger" data-delete="${post.id}">삭제</button>
                    </div>
                </div>
            </section>
        `;
        bindRouteButtons();
        app.querySelector("[data-delete]").addEventListener("click", async (e) => {
            if (!confirm("삭제하시겠습니까?")) {
                return;
            }
            try {
                await request(`/api/posts/${e.target.dataset.delete}`, { method: "DELETE" });
                location.hash = "#/posts";
            } catch (error) {
                renderError(error);
            }
        });
    } catch (error) {
        renderError(error);
    }
}

function renderCreateForm() {
    app.innerHTML = `
        <section class="panel">
            <h2>새 게시글 등록</h2>
            <form id="create-form">
                <label>제목<input name="title" maxlength="100" required /></label>
                <label>내용<textarea name="content" required></textarea></label>
                <label>태그(쉼표 구분)<input name="tags" /></label>
                <div class="row">
                    <button type="button" class="btn btn-line" data-route="#/posts">취소</button>
                    <button type="submit" class="btn btn-primary">등록</button>
                </div>
                <p class="error" id="form-error"></p>
            </form>
        </section>
    `;
    bindRouteButtons();
    app.querySelector("#create-form").addEventListener("submit", async (event) => {
        event.preventDefault();
        const formData = new FormData(event.target);
        try {
            const created = await request("/api/posts", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(Object.fromEntries(formData.entries()))
            });
            location.hash = `#/posts/${created.id}`;
        } catch (error) {
            showFormError(error);
        }
    });
}

async function renderEditForm(id) {
    setLoading();
    try {
        const post = await request(`/api/posts/${id}`);
        app.innerHTML = `
            <section class="panel">
                <h2>게시글 수정</h2>
                <form id="edit-form">
                    <label>제목<input name="title" maxlength="100" value="${escapeAttr(post.title)}" required /></label>
                    <label>내용<textarea name="content" required>${escapeHtml(post.content)}</textarea></label>
                    <label>태그(쉼표 구분)<input name="tags" value="${escapeAttr((post.tags || []).join(", "))}" /></label>
                    <div class="row">
                        <button type="button" class="btn btn-line" data-route="#/posts/${id}">취소</button>
                        <button type="submit" class="btn btn-primary">저장</button>
                    </div>
                    <p class="error" id="form-error"></p>
                </form>
            </section>
        `;
        bindRouteButtons();
        app.querySelector("#edit-form").addEventListener("submit", async (event) => {
            event.preventDefault();
            const formData = new FormData(event.target);
            try {
                const updated = await request(`/api/posts/${id}`, {
                    method: "PATCH",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(Object.fromEntries(formData.entries()))
                });
                location.hash = `#/posts/${updated.id}`;
            } catch (error) {
                showFormError(error);
            }
        });
    } catch (error) {
        renderError(error);
    }
}

async function request(url, options = {}) {
    const response = await fetch(url, options);
    const contentType = response.headers.get("content-type") || "";
    const payload = contentType.includes("application/json") ? await response.json() : null;

    if (!response.ok) {
        throw payload || { message: `요청 실패 (${response.status})` };
    }
    return payload;
}

function setLoading() {
    const template = document.getElementById("loading-template");
    app.innerHTML = "";
    app.appendChild(template.content.cloneNode(true));
}

function renderError(error) {
    const message = error?.message || "알 수 없는 오류가 발생했습니다.";
    app.innerHTML = `<section class="panel"><p class="error">${escapeHtml(message)}</p></section>`;
}

function showFormError(error) {
    const target = document.getElementById("form-error");
    if (!target) {
        return;
    }
    const fieldErrors = Array.isArray(error?.fieldErrors) ? error.fieldErrors : [];
    if (fieldErrors.length > 0) {
        target.textContent = fieldErrors.map((e) => `${e.field}: ${e.message}`).join(" / ");
        return;
    }
    target.textContent = error?.message || "요청 처리 중 오류가 발생했습니다.";
}

function buildPagination(page, totalPages) {
    if (totalPages <= 1) {
        return "";
    }

    let html = `<div class="pagination">`;
    for (let i = 1; i <= totalPages; i += 1) {
        const current = i === page ? "btn-primary" : "btn-line";
        html += `<button class="btn ${current}" data-route="#/posts?page=${i}">${i}</button>`;
    }
    html += `</div>`;
    return html;
}

function bindRouteButtons() {
    app.querySelectorAll("[data-route]").forEach((button) => {
        button.addEventListener("click", () => {
            location.hash = button.dataset.route;
        });
    });
}

function formatDate(value) {
    if (!value) {
        return "-";
    }
    return new Date(value).toLocaleString("ko-KR", {
        year: "numeric",
        month: "2-digit",
        day: "2-digit",
        hour: "2-digit",
        minute: "2-digit"
    });
}

function escapeHtml(value = "") {
    return String(value)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;");
}

function escapeAttr(value = "") {
    return escapeHtml(value).replaceAll('"', "&quot;");
}
